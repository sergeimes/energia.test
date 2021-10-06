package ee.energia.test.dao;

import ee.energia.test.domain.ShopItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopItemDao extends JpaRepository<ShopItem, Long>, JpaSpecificationExecutor<ShopItem> {
}
