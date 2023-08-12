package br.com.bbnsdevelop.inventoryservice.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.bbnsdevelop.inventoryservice.dto.InventoryDto;
import br.com.bbnsdevelop.inventoryservice.entities.Inventory;
import br.com.bbnsdevelop.inventoryservice.exceptions.NotFoundException;
import br.com.bbnsdevelop.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class INventoryService {
	
	
	private final InventoryRepository repository;
	private final ModelMapper mapper;
	
	
	public List<InventoryDto> getAll() {
		log.info("find all inventory");
		return repository.findAll().stream().map(i -> convertToDto(i)).toList();
	}

	
	public void save(InventoryDto dto) {
		Inventory entity = convertToEntity(dto);
		repository.save(entity);
		log.info("saved inventory: {}", entity);
	}

	private InventoryDto convertToDto(Inventory i) {		 
		return mapper.map(i, InventoryDto.class);
	}
	
	
	private Inventory convertToEntity(InventoryDto dto) {		 
		return mapper.map(dto, Inventory.class);
	}


	public Boolean checkStock(String skuCode) {
		Optional<Inventory> opt = repository.findByskuCode(skuCode);
		opt.orElseThrow(() -> new NotFoundException("Product ".concat(skuCode).concat(" not found")));
		return opt.isPresent();
	}
	

}
