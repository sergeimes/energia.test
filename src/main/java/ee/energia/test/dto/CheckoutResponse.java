package ee.energia.test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class CheckoutResponse {
    private BigDecimal paid;
    private BigDecimal change;
    private Map<CurrencyItem, Integer> changes;
}
