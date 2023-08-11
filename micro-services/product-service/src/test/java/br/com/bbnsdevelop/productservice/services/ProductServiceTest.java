package br.com.bbnsdevelop.productservice.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.model.Product;
import br.com.bbnsdevelop.productservice.repositories.ProductRepository;

@SpringBootTest
public class ProductServiceTest {
	
	
	@InjectMocks
	@Spy
	private ProductService service;
	
	@Mock
	private  ProductRepository repository;
	
	@Mock
	private  ModelMapper modelMapper;
	
	
	@Test
	public void shouldCreateProduct() {
		ProductDto dto = ProductDto.builder().name("Test").description("Test").price(BigDecimal.ZERO).build();
		Product p = Product.builder().name("Test").description("Test").price(BigDecimal.ZERO).build();
		Mockito.when(modelMapper.map(dto, Product.class)).thenReturn(p);
		service.save(dto);
		
		verify(service, times(1)).save(dto);
	}
	
	
	@Test
	public void shouldFindAllProducts() {
		Product p = Product.builder().id("12345sasas45454anhtg").name("Test").description("Test").price(BigDecimal.ZERO).build();
		List<Product> list = List.of(p);
		Mockito.when(repository.findAll()).thenReturn(list);
		List<ProductDto> products = service.getAllProduct();
		Assertions.assertEquals(1, products.size());
	}

}
