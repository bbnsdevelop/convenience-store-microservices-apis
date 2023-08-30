package br.com.bbnsdevelop.productservice.controllers;


import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.bbnsdevelop.productservice.dto.OrderDto;
import br.com.bbnsdevelop.productservice.services.OrderService;
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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Tag(name = "orders", description = "Order management APIs")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService service;


    @Operation(summary = "Find all orders", description = "Get a list of orders.", tags = {
            "orders" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = OrderDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/v1")
    public ResponseEntity<List<OrderDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @Operation(summary = "Return order by id", description = "This resource find order by id", tags = {"orders" })
    @ApiResponses({
            @ApiResponse(responseCode = "200", content = {
                    @Content(schema = @Schema(implementation = OrderDto.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @GetMapping("/v1/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByID(id));
    }

    @Operation(summary = "Create new order", description = "This resource provide create new order", tags = {"orders" })
    @ApiResponses({
            @ApiResponse(responseCode = "201", content = {
                    @Content(schema = @Schema(implementation = Void.class), mediaType = "application/json") }),
            @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema()) }),
            @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema()) }) })
    @PostMapping("/v1")
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public ResponseEntity<CompletableFuture<Void>> create(@Valid @RequestBody OrderDto dto){    	
    	CompletableFuture.supplyAsync(() ->service.create(dto));
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    
    
    public ResponseEntity<String> fallbackMethod(OrderDto dto, RuntimeException exception) {
    	log.error("Erro: {}", exception.getMessage());
    	return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong, please order after some time!");
    }
    

}
