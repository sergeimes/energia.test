import {Component, OnInit} from '@angular/core';
import {LoaderService} from '@app/_services/loader.service';

@Component({
  selector: 'app-cofeteria-loader',
  templateUrl: './cofeteria-loader.component.html',
  styleUrls: ['./cofeteria-loader.component.less']
})
export class CofeteriaLoaderComponent implements OnInit {
  loading: boolean;

  constructor(private loaderService: LoaderService) {
    this.loaderService.isLoading.subscribe((v) => {
      this.loading = v;
    });

  }

  ngOnInit(): void {
  }

}
