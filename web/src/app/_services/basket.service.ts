import { Injectable } from "@angular/core";
import { AddBasketRequest, Basket, CheckoutBasket, CheckoutResponse } from "@app/_models";
import { BasketClientService } from "@app/_services/basket-client.service";
import { BehaviorSubject } from "rxjs";

export const BASKET_SESSION_KEY = "basket-session";

@Injectable({ providedIn: "root" })
export class BasketService {
  basket$ = new BehaviorSubject<Basket>(null);
  checkout$ = new BehaviorSubject<CheckoutResponse>(null);

  constructor(private readonly basketClientService: BasketClientService) {
  }

  init() {
    var session = localStorage.getItem(BASKET_SESSION_KEY);
    if (session) {
      this.info();
    }
  }

  removeSession() {
    localStorage.removeItem(BASKET_SESSION_KEY);
  }

  isSessionExist() {
    var session = localStorage.getItem(BASKET_SESSION_KEY);
    if (session) {
      return true;
    }
    return false;
  }

  async getSession() {
    var session = localStorage.getItem(BASKET_SESSION_KEY);
    if (!session) {
      const basketSession = await this.basketClientService.start().toPromise();
      session = basketSession.session;
      localStorage.setItem(BASKET_SESSION_KEY, session);
    }
    return session;
  }

  async add(itemId: number) {
    var session = await this.getSession();
    const addRequest = new AddBasketRequest();
    addRequest.session = session;
    addRequest.itemId = itemId;

    this.basketClientService.add(addRequest).subscribe(response => {
      this.info();
    });
  }

  async reset() {
    var session = await this.getSession();
    this.basketClientService.reset(session).subscribe(response => {
      this.removeSession();
      this.info();
    });
  }

  async info() {
    var session = await this.getSession();
    this.basketClientService.info(session).subscribe(basketInfo => {
        this.basket$.next(basketInfo);
      });
  }

  async checkout(paid: number) {
    var session = await this.getSession();
    const checkoutBasket = new CheckoutBasket();
    checkoutBasket.session = session;
    checkoutBasket.paid = paid;
    this.basketClientService.checkout(checkoutBasket).subscribe(response => {
      this.removeSession();
      this.info();
      this.checkout$.next(response);
    });
  }
}
