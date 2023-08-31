package br.com.bbnsdevelop.productservice.controllers;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbnsdevelop.productservice.dto.ProductDto;
import br.com.bbnsdevelop.productservice.services.ProductService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "products", description = "Product management APIs")
@RestController
@RequestMapping("/api/products")
@AllArgsConstructor
@Slf4j
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

	@Operation(summary = "Create new product", description = "This resource provide create new product, name and price are mandatory.", tags = {
			"products" })
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(implementation = Void.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/v1")
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
	@TimeLimiter(name = "inventory")
	@Retry(name = "inventory")
	public ResponseEntity<CompletableFuture<String>> create(@Valid @RequestBody ProductDto dto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(CompletableFuture.supplyAsync(() ->service.save(dto)));
	}
	
	 
    public ResponseEntity<String> fallbackMethod(ProductDto dto, RuntimeException exception) {
    	log.error("Erro: {}", exception.getMessage());
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please order after some time!");
    }
    

}
