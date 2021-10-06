import {DataSource} from '@angular/cdk/table';
import {CollectionViewer} from '@angular/cdk/collections';
import {BehaviorSubject, Observable, of} from 'rxjs';
import {catchError, finalize} from 'rxjs/operators';
import { ShopItem, ShopItemListResponse } from "@app/_models/shop-item";
import { ShopItemsService } from "@app/_services/shop-items.service";

export class ShopItemsDatasource implements DataSource<ShopItem> {

  private shopItems = new BehaviorSubject<ShopItem[]>([]);
  private loading = new BehaviorSubject<boolean>(false);
  private count = new BehaviorSubject<number>(0);
  public counter$ = this.count.asObservable();

  constructor(private shopItemsService: ShopItemsService) {
  }

  connect(collectionViewer: CollectionViewer): Observable<ShopItem[]> {
    return this.shopItems.asObservable();
  }

  disconnect(collectionViewer: CollectionViewer): void {
    this.shopItems.complete();
    this.loading.complete();
    this.count.complete();
  }

  loadItems(pageNumber = 0, pageSize = 5, filter: string = '', sortField: string = '', sortDirection: string = '') {
    this.loading.next(true);
    this.shopItemsService.find({p: pageNumber, s: pageSize, sort: sortField, dir: sortDirection, term: filter})
      .pipe(
        catchError(() => of([])),
        finalize(() => this.loading.next(false))
      )
      .subscribe((result: ShopItemListResponse) => {
          this.shopItems.next(result.content);
          this.count.next(result.totalElements);
        }
      );
  }

}
