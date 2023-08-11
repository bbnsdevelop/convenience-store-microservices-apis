package br.com.bbnsdevelop.productservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.model.Product;
import br.com.bbnsdevelop.productservice.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository repository;
	private final ModelMapper modelMapper;
	 

	public List<ProductDto> getAllProduct() {
		log.info("find all product");
		
		return repository.findAll().stream().map(s -> modelMapper.map(s, ProductDto.class)).collect(Collectors.toList());
	}


	public void save(ProductDto dto) {
		Product produtc = modelMapper.map(dto, Product.class);
		log.info("saving product: {}", produtc);
		this.repository.save(produtc);
	}

}
