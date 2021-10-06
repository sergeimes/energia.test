package ee.energia.test.dao;

import ee.energia.test.domain.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BasketDao extends JpaRepository<Basket, Long>, JpaSpecificationExecutor<Basket> {

    Optional<Basket> findBySession(String session);

    List<Basket> findByCreatedBefore(Date created);
}
