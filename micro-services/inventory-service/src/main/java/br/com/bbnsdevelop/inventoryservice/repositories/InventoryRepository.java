package br.com.bbnsdevelop.inventoryservice.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.bbnsdevelop.inventoryservice.entities.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long>{

	Optional<Inventory> findByskuCode(String skuCode);

	List<Inventory> findBySkuCodeIn(List<String> skuCode);

}
