package br.com.bbnsdevelop.productservice.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.bbnsdevelop.productservice.dto.InventoryResponse;
import br.com.bbnsdevelop.productservice.dto.OrderDto;
import br.com.bbnsdevelop.productservice.dto.OrderLineItemDto;
import br.com.bbnsdevelop.productservice.entities.Order;
import br.com.bbnsdevelop.productservice.exceptions.NotFoundException;
import br.com.bbnsdevelop.productservice.exceptions.NotHasInventoryException;
import br.com.bbnsdevelop.productservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

	private final OrderRepository repository;
	private final ModelMapper modelMapper;
	private final IventoryClientService iventoryClientService;

	public List<OrderDto> getAll() {
		log.info("find all orders");
		return repository.findAll().stream().map(o -> convertToDto(o)).toList();
	}

	public OrderDto findByID(Long id) {
		log.info("find order by id: {}", id);
		return convertToDto(repository.findById(id)
				.orElseThrow(() -> new NotFoundException("Order: ".concat(id.toString()).concat(" not found"))));
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void create(OrderDto dto) {

		List<String> skuCodes = dto.getOrderLineItemsList().stream().map(OrderLineItemDto::getSkuCode).toList();

		InventoryResponse[] inventoryResponseArray = iventoryClientService.hasInStock(skuCodes);

		Boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);
		if (!allProductsInStock) {
			throw new NotHasInventoryException("Exist item  without stock in your order");
		}

		Order o = convertToEntity(dto);
		o.setOrderNumber(generateHash());
		repository.save(o);
		log.info("saved order: {}", o);
	}

	private OrderDto convertToDto(Order o) {
		return modelMapper.map(o, OrderDto.class);
	}

	private Order convertToEntity(OrderDto dto) {
		return modelMapper.map(dto, Order.class);
	}

	private String generateHash() {
		LocalDateTime now = LocalDateTime.now();
		return Math.abs(new Random().nextInt()) + "#"
				+ DateTimeFormatter.ofPattern("yyyy-MM-dd-HH:mm:ss", Locale.ENGLISH).format(now);
	}

}
