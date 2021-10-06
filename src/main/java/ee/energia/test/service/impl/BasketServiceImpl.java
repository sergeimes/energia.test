package ee.energia.test.service.impl;

import ee.energia.test.component.ShopItemConverter;
import ee.energia.test.dao.BasketDao;
import ee.energia.test.dao.BasketItemDao;
import ee.energia.test.dao.ShopItemDao;
import ee.energia.test.domain.Basket;
import ee.energia.test.domain.BasketItem;
import ee.energia.test.domain.ShopItem;
import ee.energia.test.dto.*;
import ee.energia.test.exception.BadRequestException;
import ee.energia.test.exception.BasketNotFoundException;
import ee.energia.test.exception.RecordNotFoundException;
import ee.energia.test.service.BasketService;
import ee.energia.test.utils.CurrencyUtil;
import ee.energia.test.utils.RandomUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class BasketServiceImpl implements BasketService {

    private final BasketDao basketDao;
    private final BasketItemDao basketItemDao;
    private final ShopItemDao shopItemDao;
    private final ShopItemConverter shopItemConverter;

    @Override
    @Transactional
    public BasketSession start() {
        Basket basket = new Basket();
        basket.setSession(RandomUtil.generateSession());
        basket.setCreated(new Date());
        basket = basketDao.save(basket);
        return BasketSession.builder()
                .created(basket.getCreated())
                .session(basket.getSession())
                .build();
    }

    @Override
    @Transactional
    public void reset(String sessionId) {
        Basket basket = basketDao.findBySession(sessionId)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
        basket.getItems().stream().forEach(item -> {
            basketItemDao.delete(item);
        });
        basketDao.delete(basket);
    }

    @Override
    public void resetExpiredSessions() {
        List<Basket> expiredSessions = basketDao.findByCreatedBefore(new Date());
        expiredSessions.stream().forEach(basket -> {
            basket.getItems().stream().forEach(item -> {
                basketItemDao.delete(item);
            });
            basketDao.delete(basket);
        });
    }

    @Override
    public BasketDTO info(String sessionId) {
        Basket basket = basketDao.findBySession(sessionId)
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
        return getBasketDTO(basket);
    }

    @Override
    @Transactional
    public void add(AddBasketRequest request) {
        Basket basket = basketDao.findBySession(request.getSession())
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));
        ShopItem shopItem = shopItemDao.getOne(request.getItemId());
        Optional<BasketItem> optionalItem = basket.getItems().stream()
                .filter(item -> item.getShopItem().getId().equals(request.getItemId()))
                .findAny();
        BasketItem basketItem = new BasketItem();
        Integer quantity = 0;
        if (optionalItem.isPresent()) {
            basketItem = optionalItem.get();
            ShopItemDTO shopItemDTO = shopItemConverter.convertToDTO(basketItem.getShopItem());
            if (shopItemDTO.getQuantity() <= 0) {
                throw new BadRequestException("Quantity is less than zero, not possible to add to basket");
            }
            quantity = basketItem.getQuantity();
        }
        quantity++;
        basketItem.setBasket(basket);
        basketItem.setShopItem(shopItem);
        basketItem.setQuantity(quantity);
        basketItemDao.save(basketItem);
    }

    @Override
    @Transactional
    public CheckoutResponse checkout(CheckoutBasket checkoutBasket) {
        Basket basket = basketDao.findBySession(checkoutBasket.getSession())
                .orElseThrow(() -> new BasketNotFoundException("Basket not found"));

        BasketDTO basketDTO = getBasketDTO(basket);
        BigDecimal difference = checkoutBasket.getPaid().add(basketDTO.getTotal().multiply(new BigDecimal(-1)));
        int compareResult = difference.compareTo(BigDecimal.ZERO);
        if (compareResult == -1) {
            throw new BadRequestException("Paid amount is less than total sum in basket");
        }
        basket.getItems().stream().forEach(item -> {
            item.getShopItem().setQuantity(item.getShopItem().getQuantity() - item.getQuantity());
            shopItemDao.save(item.getShopItem());
            basketItemDao.delete(item);
        });
        basketDao.delete(basket);
        return CheckoutResponse.builder()
                .paid(checkoutBasket.getPaid())
                .change(difference)
                .changes(CurrencyUtil.getChanges(difference))
                .build();
    }

    private BasketDTO getBasketDTO(Basket basket) {
        final BigDecimal[] total = {new BigDecimal(BigInteger.ZERO)};
        Integer count = basket.getItems().size();
        basket.getItems().stream().forEach(item -> {
            total[0] = total[0].add(item.getShopItem().getPrice().multiply(new BigDecimal(item.getQuantity())));
        });
        return BasketDTO.builder()
                .itemsCount(count)
                .total(total[0])
                .build();
    }


}
