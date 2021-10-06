import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { MessageService } from '@app/_services';
import { BasketService } from "@app/_services/basket.service";

@Injectable()
export class ErrorInterceptor implements HttpInterceptor {
  constructor(
    private messageService: MessageService,
    private basketService: BasketService
  ) {}

  intercept(
    request: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next.handle(request).pipe(
      catchError((err) => {
        if (err.status == 403) {
          this.basketService.removeSession();
          return;
        }
        const error = err.error.message || err.statusText;
        if (error === 'OK') {
          this.messageService.showSuccess(error);
        } else {
          this.messageService.showError(error);
        }
        return throwError(error);
      })
    );
  }
}
