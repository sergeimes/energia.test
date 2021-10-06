import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { I18nService, MenuService } from './_services';

@Component({ selector: 'app', templateUrl: 'app.component.html' })
export class AppComponent {

  constructor(
    private router: Router,
    private i18nService: I18nService,
    public menuService: MenuService
  ) {
    this.i18nService.init();
  }

}
