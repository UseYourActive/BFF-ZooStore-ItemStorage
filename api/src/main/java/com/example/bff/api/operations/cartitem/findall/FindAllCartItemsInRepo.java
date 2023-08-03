package com.example.bff.api.operations.cartitem.findall;

import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllCartItemsInRepo {
    private UUID targetItemId;
    private UUID userId;
    private BigDecimal price;
    private Integer quantity;
}
