import { Component, OnInit } from '@angular/core';
import { I18nService, SUPPORTED_LANGS } from '@app/_services';

@Component({
  selector: 'app-cofeteria-langage',
  templateUrl: './cofeteria-language.component.html',
  styleUrls: ['./cofeteria-language.component.less']
})
export class CofeteriaLanguageComponent {
  languageOptions: { key: string; value: string }[];

  constructor(private i18nService: I18nService) {}

  ngOnInit(): void {
    this.languageOptions = SUPPORTED_LANGS.map((lang) => ({
      key: lang,
      value: `language.${lang}`,
    }));
  }

  getCurrentLang() {
    return this.i18nService.language?.toUpperCase();
  }

  selectLanguage(language: string) {
    this.i18nService.setLanguage(language);
  }

  isSelectedLanguage(lang: string) {
    return this.i18nService.language === lang;
  }
}
