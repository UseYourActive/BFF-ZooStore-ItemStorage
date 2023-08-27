package com.example.bff.api.operations.cartitem.add;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddItemToCartResponse implements OperationResult {
    private String targetItemId;
    private BigDecimal price;
    private Integer quantity;
}
