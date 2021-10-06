import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";

import { DashboardComponent } from "@app/_components/dashboard";
import { ShopItemsComponent } from "@app/_components/shopitems";

const routes: Routes = [
  {
    path: "",
    component: DashboardComponent
  },
  {
    path: "admin/items",
    component: ShopItemsComponent
  },
  // otherwise redirect to home
  { path: "**", redirectTo: "" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { useHash: true })],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
