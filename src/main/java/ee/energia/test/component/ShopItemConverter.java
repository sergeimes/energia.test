package ee.energia.test.component;

import ee.energia.test.domain.ShopItem;
import ee.energia.test.dto.ShopItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class ShopItemConverter {

    public Page<ShopItemDTO> convertToDTO(Page<ShopItem> items) {
        List<ShopItemDTO> content = new LinkedList<>();
        items.forEach(shopItem -> {
            content.add(convertToDTO(shopItem));
        });
        return new PageImpl<ShopItemDTO>(content, items.getPageable(), items.getTotalElements());
    }

    public List<ShopItemDTO> convertToDTO(List<ShopItem> items) {
        List<ShopItemDTO> content = new LinkedList<>();
        items.forEach(shopItem -> {
            content.add(convertToDTO(shopItem));
        });
        return content;
    }

    public ShopItemDTO convertToDTO(ShopItem item) {
        final Integer[] basketQuantity = {0};
        if (item.getBasketItems() != null && !item.getBasketItems().isEmpty()) {
            item.getBasketItems().stream().forEach(basketItem -> {
                basketQuantity[0] = basketQuantity[0] + basketItem.getQuantity();
            });
        }
        return ShopItemDTO.builder()
                .id(item.getId())
                .name(item.getName())
                .price(item.getPrice())
                .quantity(item.getQuantity() - basketQuantity[0])
                .image(item.getImage())
                .donated(item.getDonated())
                .build();
    }
}
