package br.com.bbnsdevelop.productservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@Tag(name = "products", description = "Product management APIs")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
public class ProductController {

	private final ProductService service;

	@Operation(summary = "Find all products", description = "Get a list of product object. The response is product object with id, name, description and price.", tags = {
			"products" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = ProductDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/v1")
	public ResponseEntity<List<ProductDto>> getAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAllProduct());
	}
	
	
	@PostMapping("/v1")
	public ResponseEntity<Void> create(@RequestBody ProductDto dto){
		service.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();		
	}
	

}
