import { Injectable, TemplateRef } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class MessageService {
  private delay = 5000;

  constructor() {}

  toasts: any[] = [];

  showError(text: string) {
    this.show('error', text, {
      classname: 'bg-danger text-light',
      delay: this.delay,
    });
  }

  showWarning(text: string) {
    this.show('error', text, {
      classname: 'bg-warning text-light',
      delay: this.delay,
    });
  }

  showSuccess(text: string) {
    this.show('success', text, {
      classname: 'bg-success text-light',
      delay: this.delay,
    });
  }

  show(type: string, text: string, options: any = {}) {
    const toastItem = new ToastItem(type, text);
    this.toasts.push({ toastItem, ...options });
  }

  remove(toast) {
    this.toasts = this.toasts.filter((t) => t !== toast);
  }
}

export class ToastItem {
  type: string;
  message: string;

  constructor(type: string, message: string) {
    this.type = type;
    this.message = message;
  }
}
