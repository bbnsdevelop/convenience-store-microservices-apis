package br.com.bbnsdevelop.productservice.services;

import br.com.bbnsdevelop.productservice.exceptions.NotFoundException;
import br.com.bbnsdevelop.productservice.dto.OrderDto;
import br.com.bbnsdevelop.productservice.entities.Order;
import br.com.bbnsdevelop.productservice.repositories.OrderLineItemRepository;
import br.com.bbnsdevelop.productservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository repository;
    private final OrderLineItemRepository itemRepository;
    private final ModelMapper modelMapper;

    public List<OrderDto> getAll() {
        log.info("find all orders");
        return repository.findAll().stream().map(o -> convertToDto(o)).toList();
    }

    public OrderDto findByID(Long id){
        log.info("find order by id: {}", id);
        return convertToDto(repository.findById(id).orElseThrow(() -> new NotFoundException("Order: ".concat(id.toString()).concat(" not found"))));
    }

    public void create(OrderDto dto) {
        Order o = convertToEntity(dto);
        repository.save(o);
        log.info("saved order: {}", o);
    }

    private OrderDto convertToDto(Order o){
        return modelMapper.map(o, OrderDto.class);
    }

    private Order convertToEntity(OrderDto dto){
        return modelMapper.map(dto, Order.class);
    }
}