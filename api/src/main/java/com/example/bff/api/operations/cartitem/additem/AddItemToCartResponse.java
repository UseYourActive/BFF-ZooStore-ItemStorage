package com.example.bff.api.operations.cartitem.additem;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddItemToCartResponse implements OperationResult {
    private UUID targetItemId;
    private BigDecimal price;
    private Integer quantity;
}
