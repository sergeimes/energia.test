
export interface ShopItemListResponse {
  content: ShopItem[];
  totalElements: number;
}

export class ShopItem {
  id: number;
  name: string;
  price: number;
  quantity: number;
  image: string;
  donated: boolean;
}
