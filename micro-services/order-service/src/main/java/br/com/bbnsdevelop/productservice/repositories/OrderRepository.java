package br.com.bbnsdevelop.productservice.repositories;

import br.com.bbnsdevelop.productservice.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
}
