package ee.energia.test.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShopItemDTO {
    private Long id;
    private String name;
    private BigDecimal price;
    private Integer quantity;
    private String image;
    private Boolean donated;
}
