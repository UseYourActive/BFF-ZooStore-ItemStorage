package com.example.bff.api.operations.cartitem.removeitem;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartRepo {
    private UUID targetItemId;
    private UUID userId;
    private BigDecimal price;
    private Integer quantity;
}
