package ee.energia.test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class BasketDTO {
    private Integer itemsCount;
    private BigDecimal total;
}
