import { Component, OnInit } from "@angular/core";
import { MatDialogRef } from "@angular/material/dialog";
import { ChangeItem, CheckoutResponse, ShopItem } from "@app/_models";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MessageService } from "@app/_services";
import { BasketService } from "@app/_services/basket.service";
import { tap } from "rxjs/operators";

@Component({
  selector: "app-checkout-dialog",
  templateUrl: "./checkout.component.html",
  styleUrls: ["./checkout.component.less"]
})
export class CheckoutComponent implements OnInit {
  checkoutForm: FormGroup;
  checkoutResponse: CheckoutResponse;
  basketTotalCount: number = 0;

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private basketService: BasketService,
    public dialogRef: MatDialogRef<ShopItem>) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.basketService.basket$
      .pipe(
        tap((basket) => {
          if (basket != null) {
            this.basketTotalCount = basket.itemsCount;
          }
        })
      )
      .subscribe();

    this.checkoutForm = this.formBuilder.group({
      paid: new FormControl(0, [
        Validators.required,
        Validators.min(1),
        Validators.pattern("(?!(^0+(\\.0+)?$))^\\d{1,4}(\\.\\d{1,2})?$")
      ])
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.checkoutForm.controls;
  }

  isProcessDisabled() {
    return this.checkoutForm.invalid || this.basketTotalCount == 0;
  }

  convertMapToList(): ChangeItem[] {
    const changes: ChangeItem[] = [];
    let keys = Object.keys(this.checkoutResponse.changes);
    keys.forEach(key => {
      const change = new ChangeItem();
      change.currency = key;
      change.value = this.checkoutResponse.changes[key];
      changes.push(change);
    });
    return changes;
  }

  onSubmit() {
    // stop here if form is invalid
    if (this.checkoutForm.invalid) {
      return;
    }
    this.basketService.checkout$.subscribe();
    this.basketService.checkout$
      .pipe(
        tap((checkoutResponse) => {
          if (checkoutResponse != null) {
            this.checkoutResponse = checkoutResponse;
          }
        })
      )
      .subscribe();
    this.basketService.checkout(this.f.paid.value);
  }
}
