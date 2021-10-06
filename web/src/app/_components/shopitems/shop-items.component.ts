import {Component, OnInit, ViewChild} from '@angular/core';
import {tap} from 'rxjs/operators';
import {MatPaginator} from '@angular/material/paginator';
import {MatSort, Sort} from '@angular/material/sort';
import {MatDialog} from '@angular/material/dialog';
import { ShopItemsDatasource } from "@app/_datasources/shop-items.datasource";
import { ShopItemsService } from "@app/_services/shop-items.service";
import { ShopItemEditComponent } from "@app/_components/shopitems/edit/shop-item-edit.component";

@Component({
  selector: 'app-shop-items',
  templateUrl: './shop-items.component.html',
  styleUrls: ['./shop-items.component.less']
})
export class ShopItemsComponent implements OnInit {
  shopItemsDataSource: ShopItemsDatasource;
  filter = '';
  displayedColumns = ['name', 'price', 'quantity', 'image', 'donated'];

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private shopItemsService: ShopItemsService,
              public dialog: MatDialog) {
  }

  ngOnInit() {
    this.shopItemsDataSource = new ShopItemsDatasource(this.shopItemsService);
    this.shopItemsDataSource.loadItems(0, 5, '', 'name', 'desc');
  }

  // tslint:disable-next-line:use-lifecycle-interface
  ngAfterViewInit() {
    this.shopItemsDataSource.counter$
      .pipe(
        tap((count) => {
          this.paginator.length = count;
        })
      )
      .subscribe();

    this.paginator.page
      .pipe(
        tap(() => this.loadItems())
      )
      .subscribe();
  }

  loadItems() {
    this.shopItemsDataSource.loadItems(this.paginator.pageIndex, this.paginator.pageSize,
      this.filter, this.sort.active, this.sort.direction);
  }

  applyFilter(filterValue: string) {
    this.filter = filterValue.trim(); // Remove whitespace
    this.filter = filterValue.toLowerCase(); // Datasource defaults to lowercase matches
    this.paginator.firstPage();
    this.loadItems();
  }

  applySort(sort: Sort) {
    this.sort.active = sort.active;
    this.sort.direction = sort.direction;
    this.paginator.firstPage();
    this.loadItems();
  }

  openDialog(row: any): void {
    const dialogRef = this.dialog.open(ShopItemEditComponent, {
      width: '650px',
      data: row,
    });

    dialogRef.afterClosed().subscribe(result => {
      this.loadItems();
    });
  }
}
