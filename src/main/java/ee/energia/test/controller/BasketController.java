package ee.energia.test.controller;

import ee.energia.test.dto.*;
import ee.energia.test.service.BasketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Slf4j
@RestController
@RequestMapping("/api/basket")
@RequiredArgsConstructor
@CafeteriaAPI
public class BasketController {

    private final BasketService basketService;

    @GetMapping("/start")
    public BasketSession start() {
        return basketService.start();
    }

    @GetMapping("/reset/{sessionId}")
    public ResponseEntity<?> reset(@PathVariable String sessionId) {
        basketService.reset(sessionId);
        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @PostMapping("/checkout")
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutBasket checkoutBasket) {
        return basketService.checkout(checkoutBasket);
    }

    @GetMapping("/info/{sessionId}")
    public BasketDTO info(@PathVariable String sessionId) {
        return basketService.info(sessionId);
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@Valid @RequestBody AddBasketRequest request) {
        basketService.add(request);
        return new ResponseEntity<String>(HttpStatus.OK);
    }
}
