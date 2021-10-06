package ee.energia.test.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Builder
public class BasketItemDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String image;
}
