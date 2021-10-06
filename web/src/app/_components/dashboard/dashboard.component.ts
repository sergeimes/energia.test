import { Component, OnInit } from "@angular/core";
import { ShopItemsService } from "@app/_services/shop-items.service";
import { ShopItem } from "@app/_models/shop-item";
import { BasketService } from "@app/_services/basket.service";
import { MessageService } from "@app/_services";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.less'],
})
export class DashboardComponent implements OnInit {
  items: ShopItem[];

  constructor(
    private shopItemsService: ShopItemsService,
    private basketService: BasketService,
    private messageService: MessageService
  ) {
  }

  ngOnInit() {
    this.shopItemsService.getAll().subscribe(response => {
      this.items = response;
    });
  }

  addItemToBasket(item: ShopItem) {
    if (item.quantity > 0) {
      this.basketService.add(item.id);
    } else {
      this.messageService.showWarning("Quantity is over!");
    }
  }
}
