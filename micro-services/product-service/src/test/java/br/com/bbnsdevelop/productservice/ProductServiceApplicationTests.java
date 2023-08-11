package br.com.bbnsdevelop.productservice;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.services.ProductService;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
	    "openapi.dev-url=testValue",
	    "openapi.prod-url=testValue",
	})
class ProductServiceApplicationTests {

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ProductService productRepository;

	static {
		mongoDBContainer.start();
	}

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
		dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductDto productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/products/v1").contentType(MediaType.APPLICATION_JSON)
				.content(productRequestString)).andExpect(status().isCreated());
		Assertions.assertEquals(1, productRepository.getAllProduct().size());
	}

	private ProductDto getProductRequest() {
		return ProductDto.builder().name("iPhone 13").description("iPhone 13").price(BigDecimal.valueOf(1200)).build();
	}

}
