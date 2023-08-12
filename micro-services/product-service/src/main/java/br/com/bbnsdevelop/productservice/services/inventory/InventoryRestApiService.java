package br.com.bbnsdevelop.productservice.services.inventory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import br.com.bbnsdevelop.productservice.exceptions.IntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryRestApiService {

	@Value("${inventory-service.url}")
	private String url;

	
	private final RestTemplate api;

	public void save(String id, Integer qtd) {
		InventoryDto dto = InventoryDto.builder().skuCode(id).quantity(qtd).build();	
		ResponseEntity<?> responseEntity = api.postForEntity(url + "/api/inventories/v1", dto, Object.class);

		if (!responseEntity.getStatusCode().equals(HttpStatus.CREATED)) {
			String msg = (String) responseEntity.getBody();
			throw new IntegrationException("Error when call service inventory: " + msg);
		}
		log.info("Product sent with sucess to inventory");
	}

}
