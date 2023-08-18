package br.com.bbnsdevelop.inventoryservice.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbnsdevelop.inventoryservice.dto.InventoryDto;
import br.com.bbnsdevelop.inventoryservice.dto.InventoryResponse;
import br.com.bbnsdevelop.inventoryservice.services.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
@Tag(name = "inventories", description = "Inventory management APIs")
@Slf4j
public class InventoryController {

	private final InventoryService service;

	@Operation(summary = "Find all inventories", description = "Get a list of inventories.", tags = { "inventories" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = InventoryDto.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/v1")
	public ResponseEntity<List<InventoryDto>> findAll() {
		return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
	}
	
	
	
	@Operation(summary = "Is in stock", description = "Check if has in stock.", tags = { "inventories" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/v1/{sku-code}")
	public ResponseEntity<Boolean> isInStock(@PathVariable("sku-code") String skuCode ) {
		return ResponseEntity.status(HttpStatus.OK).body(service.isInStock(skuCode));
	}
	
	@Operation(summary = "Is in stock", description = "Check if has in stock.", tags = { "inventories" })
	@ApiResponses({
			@ApiResponse(responseCode = "200", content = {
					@Content(schema = @Schema(implementation = Boolean.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@GetMapping("/v2")
	public ResponseEntity<List<InventoryResponse>> isInStock(@RequestParam("skuCode") List<String> skuCode ) {
		log.info("Received inventory check request for skuCode: {}", skuCode);
		return ResponseEntity.status(HttpStatus.OK).body(service.isInStock(skuCode));
	}
	
	
	
	@Operation(summary = "Save inventory", description = "Save.", tags = { "inventories" })
	@ApiResponses({
			@ApiResponse(responseCode = "201", content = {
					@Content(schema = @Schema(implementation = Void.class), mediaType = "application/json") }),
			@ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
			@ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
	@PostMapping("/v1")
	public ResponseEntity<Void> create( @Valid @RequestBody InventoryDto dto ) {
		service.save(dto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
