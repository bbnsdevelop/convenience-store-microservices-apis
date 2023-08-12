package br.com.bbnsdevelop.inventoryservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class InventoryDto {

	private Long id;

	@NotNull(message = "skuCode is mandatory")
	private String skuCode;

	
	@NotNull(message = "quantity is mandatory")
	private Integer quantity;

}
