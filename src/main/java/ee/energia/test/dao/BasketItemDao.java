package ee.energia.test.dao;

import ee.energia.test.domain.BasketItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BasketItemDao extends JpaRepository<BasketItem, Long>, JpaSpecificationExecutor<BasketItem> {
}
