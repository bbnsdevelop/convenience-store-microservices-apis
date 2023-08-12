package br.com.bbnsdevelop.productservice.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderLineItemDto {

    private Long id;

    @NotEmpty(message = "skuCode is mandatory")
    private String skuCode;

    @NotNull(message = "price is mandatory")
    private BigDecimal price;

    @NotNull(message = "quantity is mandatory")
    private Integer quantity;
}
