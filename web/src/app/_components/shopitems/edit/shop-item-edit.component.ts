import { Component, Inject, OnInit } from "@angular/core";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
import { ShopItem } from "@app/_models";
import { FormBuilder, FormControl, FormGroup, Validators } from "@angular/forms";
import { MessageService, ShopItemsService } from "@app/_services";

@Component({
  selector: "app-shopitem-dialog",
  templateUrl: "./shop-item-edit.component.html",
  styleUrls: ["./shop-item-edit.component.less"]
})
export class ShopItemEditComponent implements OnInit {
  shopItemForm: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService,
    private shopItemsService: ShopItemsService,
    public dialogRef: MatDialogRef<ShopItem>,
    @Inject(MAT_DIALOG_DATA) public shopItem: ShopItem) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.shopItemForm = this.formBuilder.group({
      name: new FormControl(this.shopItem.name, [
        Validators.required
      ]),
      price: new FormControl(this.shopItem.price, [
        Validators.required
      ]),
      quantity: new FormControl(this.shopItem.quantity, []),
      image: new FormControl(this.shopItem.image, [
        Validators.required
      ]),
      donated: new FormControl(this.shopItem.donated, [
      ])
    });
  }

  // convenience getter for easy access to form fields
  get f() {
    return this.shopItemForm.controls;
  }

  onSubmit() {
    // stop here if form is invalid
    if (this.shopItemForm.invalid) {
      return;
    }
    if (this.shopItem === undefined) {
      this.shopItem = new ShopItem();
    }
    this.shopItem.name = this.f.name.value;
    this.shopItem.price = this.f.price.value;
    this.shopItem.quantity = this.f.quantity.value;
    this.shopItem.image = this.f.image.value;
    this.shopItem.donated = this.f.donated.value;

    this.shopItemsService.save(this.shopItem).subscribe(
      response => {
        this.messageService.showSuccess("ShopItem saved");
        this.dialogRef.close();
      }
    );
  }
}
