import { Injectable } from '@angular/core';
import { LangChangeEvent, TranslateService } from '@ngx-translate/core';

import en from '../../translations/en.json';
import ru from '../../translations/ru.json';
import et from '../../translations/et.json';

import { DEFAULT_LANG, ALL_LANGS } from '../_models/language';

import { Logger } from './logger.service';

const log = new Logger('I18nService');

export const LANG_KEY = 'cofeteria-language';
export const SUPPORTED_LANGS = ALL_LANGS;

@Injectable()
export class I18nService {
  constructor(private translateService: TranslateService) {
    // Embed languages to avoid extra HTTP requests
    translateService.setTranslation('ru', ru);
    translateService.setTranslation('en', en);
    translateService.setTranslation('et', et);
  }

  init() {
    this.setLanguage(localStorage.getItem(LANG_KEY) || DEFAULT_LANG);
    this.translateService.onLangChange.subscribe((event: LangChangeEvent) => {
      localStorage.setItem(LANG_KEY, event.lang);
    });
  }

  setLanguage(language: string) {
    language = language || DEFAULT_LANG;
    if (!SUPPORTED_LANGS.includes(language)) {
      log.debug(`Unsupported language: ${language}`);
      return;
    }
    log.debug(`Language set to ${language}`);
    this.translateService.use(language);
  }

  get language(): string {
    return this.translateService.currentLang;
  }
}
