package br.com.bbnsdevelop.productservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.bbnsdevelop.productservice.dto.InventoryResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IventoryClientService {

	
	private final WebClient.Builder webClientBuilder;
	
	@Value("${server-inventory.url}")
	private String inventory;
	
	
	public Boolean hasInStock(String code) {		
		String url = inventory.concat("/api/inventories/v1/").concat(code);
		
		log.info("call a service inventory to check if has item in stock: {}", url);
		return webClientBuilder.build().get().uri(url).retrieve().bodyToMono(Boolean.class).block();
	}
	
	public InventoryResponse[] hasInStock(List<String> skuCodes) {		
		String url = inventory.concat("/api/inventories/v2");
		
		log.info("call a service inventory to check if has item in stock: {}", url);
		return webClientBuilder.build().get().uri(url, uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build()).retrieve().bodyToMono(InventoryResponse[].class).block();
	}
	
}
