import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from "@angular/core";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { HTTP_INTERCEPTORS, HttpClientModule } from "@angular/common/http";

import { AppComponent } from "./app.component";
import { AppRoutingModule } from "./app-routing.module";

import { DashboardComponent } from "./_components/dashboard";
import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { NgbModule } from "@ng-bootstrap/ng-bootstrap";
import { MatInputModule } from "@angular/material/input";
import { MatProgressBarModule } from "@angular/material/progress-bar";
import { MatDialogModule } from "@angular/material/dialog";
import { MatCheckboxModule } from "@angular/material/checkbox";
import { MatButtonModule } from "@angular/material/button";
import { MatSelectModule } from "@angular/material/select";
import { FlexLayoutModule } from "@angular/flex-layout";
import { CofeteriaMessageComponent } from "./_components/cofeteria-message/cofeteria-message.component";
import { MatNativeDateModule } from "@angular/material/core";
import { DragDropModule } from "@angular/cdk/drag-drop";
import { MatCardModule } from "@angular/material/card";
import { CookieService } from "ngx-cookie-service";
import { FormatTimePipe } from "@app/_directives/format-time-pipe";
import { TranslateModule } from "@ngx-translate/core";
import { I18nService } from "@app/_services";
import { MatIconModule } from "@angular/material/icon";
import { MatDividerModule } from "@angular/material/divider";
import { ErrorInterceptor } from "@app/_helpers";
import { ShopItemsComponent } from "@app/_components/shopitems";
import { MatTableModule } from "@angular/material/table";
import { CofeteriaMenuComponent } from "@app/_components/cofeteria-menu/cofeteria-menu.component";
import { CofeteriaLoaderComponent } from "@app/_components/cofeteria-loader/cofeteria-loader.component";
import { LoaderInterceptorService } from "@app/_helpers/loader-interceptor.service";
import { MatPaginatorModule } from "@angular/material/paginator";
import { MatSortModule } from "@angular/material/sort";
import { CofeteriaBasketComponent } from "@app/_components/cofeteria-basket/cofeteria-basket.component";
import { ShopItemEditComponent } from "@app/_components/shopitems/edit/shop-item-edit.component";
import { CheckoutComponent } from "@app/_components/checkout/checkout.component";
import { CofeteriaLanguageComponent } from "@app/_components/language-menu/cofeteria-language.component";

@NgModule({
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    TranslateModule.forRoot({ defaultLanguage: "en", isolate: false }),
    TranslateModule,
    NgbModule,
    MatInputModule,
    MatSelectModule,
    MatProgressBarModule,
    MatIconModule,
    MatDialogModule,
    FormsModule,
    FlexLayoutModule,
    MatCheckboxModule,
    MatButtonModule,
    MatNativeDateModule,
    DragDropModule,
    MatCardModule,
    MatDividerModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule
  ],
  entryComponents: [
    ShopItemEditComponent, CheckoutComponent
  ],
  declarations: [
    AppComponent,
    DashboardComponent,
    CofeteriaMenuComponent,
    CofeteriaLanguageComponent,
    CofeteriaMessageComponent,
    FormatTimePipe,
    ShopItemsComponent,
    CofeteriaLoaderComponent,
    CofeteriaBasketComponent,
    ShopItemEditComponent,
    CheckoutComponent
  ],
  schemas: [CUSTOM_ELEMENTS_SCHEMA],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
    {provide: HTTP_INTERCEPTORS, useClass: LoaderInterceptorService, multi: true},
    CofeteriaMenuComponent, CofeteriaLanguageComponent, CookieService,
    I18nService
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
