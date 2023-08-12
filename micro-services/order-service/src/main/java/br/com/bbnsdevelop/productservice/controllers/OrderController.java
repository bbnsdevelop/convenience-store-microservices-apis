package br.com.bbnsdevelop.productservice.controllers;


import br.com.bbnsdevelop.productservice.dto.OrderDto;
import br.com.bbnsdevelop.productservice.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;


    @GetMapping("/v1")
    public ResponseEntity<List<OrderDto>> findAll(){
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll());
    }

    @GetMapping("/v1/{id}")
    public ResponseEntity<OrderDto> findOrderById(@PathVariable("id") Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findByID(id));
    }

    public ResponseEntity<Void> create(OrderDto dto){
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
