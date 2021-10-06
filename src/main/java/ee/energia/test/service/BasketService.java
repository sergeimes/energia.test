package ee.energia.test.service;

import ee.energia.test.dto.*;

public interface BasketService {

    BasketSession start();

    void reset(String sessionId);

    void resetExpiredSessions();

    BasketDTO info(String sessionId);

    void add(AddBasketRequest request);

    CheckoutResponse checkout(CheckoutBasket checkoutBasket);
}
