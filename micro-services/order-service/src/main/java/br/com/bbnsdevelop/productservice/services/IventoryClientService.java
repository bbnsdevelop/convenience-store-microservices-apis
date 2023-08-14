package br.com.bbnsdevelop.productservice.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class IventoryClientService {

	
	private final WebClient webClient;
	
	@Value("${server-inventory.url}")
	private String inventory;
	
	
	public Boolean hasInStock(String code) {		
		String url = inventory.concat("/api/inventories/v1/").concat(code);
		
		log.info("call a service inventory to check if has item in stock: {}", url);
		return webClient.get().uri(url).retrieve().bodyToMono(Boolean.class).block();
	}
	
}
