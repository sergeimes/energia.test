import { Injectable } from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  sidebarClass = '';

  toggleSidebar() {
    if (this.sidebarClass === '') {
      this.sidebarClass = 'active';
    } else {
      this.sidebarClass = '';
    }
  }
}
