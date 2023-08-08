package br.com.bbnsdevelop.productservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.services.ProductService;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {
	
	private final ProductService service;
	
	
	@GetMapping
	public ResponseEntity<List<ProductDto>> getAll(){
		return ResponseEntity.status(HttpStatus.OK).body(service.getAllProduct());
	}

}
