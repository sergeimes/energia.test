import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';

import {environment} from '@environments/environment';
import { ShopItem } from "@app/_models/shop-item";

@Injectable({providedIn: 'root'})
export class ShopItemsService {
  constructor(private http: HttpClient) {
  }

  find(request) {
    const params = request;
    return this.http.get(`${environment.apiUrl}/api/shopitems/find`, {params});
  }

  getAll() {
    return this.http.get<ShopItem[]>(`${environment.apiUrl}/api/shopitems/all`);
  }

  get(id: number) {
    return this.http.get<ShopItem>(`${environment.apiUrl}/api/shopitems/${id}`);
  }

  save(shopItem: ShopItem) {
    return this.http.post<ShopItem>(`${environment.apiUrl}/api/shopitems/save`, shopItem);
  }
}
