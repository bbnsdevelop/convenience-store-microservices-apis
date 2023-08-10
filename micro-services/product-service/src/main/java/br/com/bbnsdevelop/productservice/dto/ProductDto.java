package br.com.bbnsdevelop.productservice.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ProductDto {
	
	private String id;
	
	@NotBlank(message = "Name is mandatory")
    private String name;	
	
    private String description;
    
    @NotNull(message = "price is mandatory")
    private BigDecimal price;

}
