import { Component, OnInit } from "@angular/core";
import { BasketService } from "@app/_services/basket.service";
import { tap } from "rxjs/operators";
import { MatDialog } from "@angular/material/dialog";
import { CheckoutComponent } from "@app/_components/checkout/checkout.component";

@Component({
  selector: "app-cofeteria-basket",
  templateUrl: "./cofeteria-basket.component.html",
  styleUrls: ["./cofeteria-basket.component.less"]
})
export class CofeteriaBasketComponent implements OnInit {
  total: number;
  items: number;

  constructor(public basketService: BasketService,
              public dialog: MatDialog) {
  }

  ngOnInit(): void {
    this.basketService.init();
    this.basketService.basket$
      .pipe(
        tap((basket) => {
          if (basket != null) {
            this.items = basket.itemsCount;
            this.total = basket.total;
          }
        })
      )
      .subscribe();
  }

  basketRest() {
    this.basketService.reset();
  }

  basketCheckout() {
    const dialogRef = this.dialog.open(CheckoutComponent, {
      width: "650px"
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }
}
