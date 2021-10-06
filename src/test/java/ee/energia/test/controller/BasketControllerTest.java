package ee.energia.test.controller;

import ee.energia.test.dto.BasketDTO;
import ee.energia.test.dto.BasketSession;
import ee.energia.test.dto.CheckoutBasket;
import ee.energia.test.dto.CheckoutResponse;
import ee.energia.test.service.BasketService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BasketControllerTest {

    private static final String SESSION_ID = "TESTSESSIONID";

    @Mock
    private BasketService basketService;

    @InjectMocks
    private BasketController basketController;

    public final CheckoutBasket checkoutBasket = CheckoutBasket.builder().session(SESSION_ID).paid(new BigDecimal(100)).build();

    @BeforeEach
    void setMockOutput() {
        when(basketService.start()).thenReturn(BasketSession.builder().session(SESSION_ID).build());
        when(basketService.info(SESSION_ID)).thenReturn(BasketDTO.builder().itemsCount(1).total(new BigDecimal(10)).build());
        when(basketService.checkout(checkoutBasket)).thenReturn(CheckoutResponse.builder().change(new BigDecimal(10)).build());
    }

    @Test
    public void shouldReturnCorrectBasketSession() {
        BasketSession basketSession = basketController.start();
        assertThat(basketSession).isNotEqualTo(null);
        assertThat(basketSession.getSession()).isEqualTo(SESSION_ID);
    }

    @Test
    public void shouldReturnCorrectBasketInfo() {
        BasketDTO basketDTO = basketController.info(SESSION_ID);
        assertThat(basketDTO).isNotEqualTo(null);
        assertThat(basketDTO.getTotal()).isEqualTo(new BigDecimal(10));
        assertThat(basketDTO.getItemsCount()).isEqualTo(1);
    }

    @Test
    public void shouldReturnCorrectReset() {
        ResponseEntity<?> response = basketController.reset(SESSION_ID);
        assertThat(response).isNotEqualTo(null);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void shouldReturnCorrectCheckoutResponse() {
        CheckoutResponse checkoutResponse = basketController.checkout(checkoutBasket);
        assertThat(checkoutResponse).isNotEqualTo(null);
        assertThat(checkoutResponse.getChange()).isEqualTo(new BigDecimal(10));
    }

}