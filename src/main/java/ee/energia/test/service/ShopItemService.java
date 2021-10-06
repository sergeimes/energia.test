package ee.energia.test.service;

import ee.energia.test.common.CustomFilter;
import ee.energia.test.dto.ShopItemDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ShopItemService {

    Page<ShopItemDTO> findItems(CustomFilter filter);

    List<ShopItemDTO> getAll();

    ShopItemDTO findById(Long id);

    ShopItemDTO save(ShopItemDTO itemDTO);
}
