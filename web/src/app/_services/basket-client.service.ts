import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

import { environment } from "@environments/environment";
import { AddBasketRequest, Basket, BasketSession, CheckoutBasket, CheckoutResponse } from "@app/_models";

@Injectable({providedIn: 'root'})
export class BasketClientService {
  constructor(private http: HttpClient) {
  }

  start() {
    return this.http.get<BasketSession>(`${environment.apiUrl}/api/basket/start`);
  }

  reset(sessionId) {
    return this.http.get(`${environment.apiUrl}/api/basket/reset/${sessionId}`);
  }

  checkout(checkoutRequest: CheckoutBasket) {
    return this.http.post<CheckoutResponse>(`${environment.apiUrl}/api/basket/checkout`, checkoutRequest);
  }

  info(sessionId) {
    return this.http.get<Basket>(`${environment.apiUrl}/api/basket/info/${sessionId}`);
  }

  add(addRequest: AddBasketRequest) {
    return this.http.post(`${environment.apiUrl}/api/basket/add`, addRequest);
  }
}
