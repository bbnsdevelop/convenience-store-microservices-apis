package br.com.bbnsdevelop.inventoryservice.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.bbnsdevelop.inventoryservice.dto.InventoryDto;
import br.com.bbnsdevelop.inventoryservice.dto.InventoryResponse;
import br.com.bbnsdevelop.inventoryservice.entities.Inventory;
import br.com.bbnsdevelop.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

	private final InventoryRepository repository;
	private final ModelMapper mapper;

	public List<InventoryDto> getAll() {
		log.info("find all inventory");
		return repository.findAll().stream().map(i -> convertToDto(i)).toList();
	}

	public void save(InventoryDto dto) {
		Inventory entity = null;
		Optional<Inventory> opt = checkStock(dto.getSkuCode());
		if (opt.isPresent()) {
			entity = opt.get();
			entity.setQuantity(dto.getQuantity());
		} else {
			entity = convertToEntity(dto);
		}

		repository.save(entity);
		log.info("saved inventory: {}", entity);
	}

	public Boolean isInStock(String skuCode) {
		Optional<Inventory> opt = checkStock(skuCode);
		if (opt.isPresent()) {
			return opt.get().getQuantity() >= 1;
		} else {
			return false;
		}
	}

	public List<InventoryResponse> isInStock(List<String> skuCode) {
		log.info("Checking Inventory");
		return repository.findBySkuCodeIn(skuCode).stream().map(inventory -> InventoryResponse.builder()
				.skuCode(inventory.getSkuCode()).isInStock(inventory.getQuantity() > 0).build()).toList();
	}

	private Optional<Inventory> checkStock(String skuCode) {
		Optional<Inventory> opt = repository.findByskuCode(skuCode);
		return opt;
	}

	private InventoryDto convertToDto(Inventory i) {
		return mapper.map(i, InventoryDto.class);
	}

	private Inventory convertToEntity(InventoryDto dto) {
		return mapper.map(dto, Inventory.class);
	}

}
