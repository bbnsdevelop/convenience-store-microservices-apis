package br.com.bbnsdevelop.productservice.repositories;

import br.com.bbnsdevelop.productservice.entities.OrderLineItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemRepository extends JpaRepository<OrderLineItem, Long> {
}
