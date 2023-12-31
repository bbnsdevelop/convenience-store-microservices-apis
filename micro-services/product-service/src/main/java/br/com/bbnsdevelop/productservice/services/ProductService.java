package br.com.bbnsdevelop.productservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.model.Product;
import br.com.bbnsdevelop.productservice.repositories.ProductRepository;
import br.com.bbnsdevelop.productservice.services.inventory.InventoryRestApiService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository repository;
	private final ModelMapper modelMapper;
	
	@Autowired
	private InventoryRestApiService serviceInventory;
	 

	public List<ProductDto> getAllProduct() {
		log.info("find all product");
		
		return repository.findAll().stream().map(s -> modelMapper.map(s, ProductDto.class)).collect(Collectors.toList());
	}


	@Transactional
	public String save(ProductDto dto) {
		Product produtc = modelMapper.map(dto, Product.class);
		this.repository.save(produtc);
		log.info("saving product: {}", produtc);
			
		log.info("sendiing product to inventory: {}", produtc);
		serviceInventory.save(produtc.getId(), produtc.getQuantity());	
		return "Created with success";
	}

}
