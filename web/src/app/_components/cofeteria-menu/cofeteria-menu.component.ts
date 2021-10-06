import { Component } from "@angular/core";
import { MenuService } from "@app/_services";

@Component({
  selector: 'app-cofeteria-menu',
  templateUrl: './cofeteria-menu.component.html',
  styleUrls: ['./cofeteria-menu.component.less']
})
export class CofeteriaMenuComponent {
  isAdminMenuCollapsed = false;

  constructor(public menuService: MenuService) {
  }

}
