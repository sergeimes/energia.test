package ee.energia.test.service.impl;

import ee.energia.test.common.CustomFilter;
import ee.energia.test.component.ShopItemConverter;
import ee.energia.test.dao.ShopItemDao;
import ee.energia.test.dao.specification.GenerericSpecification;
import ee.energia.test.dao.specification.GenericSpecificationsBuilder;
import ee.energia.test.dao.specification.SearchOperation;
import ee.energia.test.dao.specification.SpecSearchCriteria;
import ee.energia.test.domain.Basket;
import ee.energia.test.domain.ShopItem;
import ee.energia.test.dto.ShopItemDTO;
import ee.energia.test.exception.RecordNotFoundException;
import ee.energia.test.service.ShopItemService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShopItemServiceImpl implements ShopItemService {

    private final ShopItemDao shopItemDao;
    private final ShopItemConverter shopItemConverter;

    @Override
    public Page<ShopItemDTO> findItems(CustomFilter filter) {
        return shopItemConverter.convertToDTO(shopItemDao.findAll(filter(filter), filter.getPageable()));
    }

    @Override
    public List<ShopItemDTO> getAll() {
        return shopItemConverter.convertToDTO(shopItemDao.findAll());
    }

    @Override
    public ShopItemDTO findById(Long id) {
        return shopItemConverter.convertToDTO(shopItemDao.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("ShopItem not found")));
    }

    @Override
    @Transactional
    public ShopItemDTO save(ShopItemDTO itemDTO) {
        ShopItem shopItem = shopItemDao.findById(itemDTO.getId())
                .orElseThrow(() -> new RecordNotFoundException("ShopItem not found"));
        shopItem.setName(itemDTO.getName());
        shopItem.setQuantity(itemDTO.getQuantity());
        shopItem.setImage(itemDTO.getImage());
        shopItem.setPrice(itemDTO.getPrice());
        shopItem.setDonated(itemDTO.getDonated());
        shopItem = shopItemDao.save(shopItem);

        return shopItemConverter.convertToDTO(shopItem);
    }

    private Specification<ShopItem> filter(CustomFilter filter) {
        GenericSpecificationsBuilder<ShopItem> builder = new GenericSpecificationsBuilder<>();

        if (StringUtils.isNotEmpty(filter.getStringField("term"))) {
            builder.with(new SpecSearchCriteria("name", SearchOperation.CONTAINS, filter.getStringField("term")));
        }
        return builder.build(GenerericSpecification<ShopItem>::new);
    }

}
