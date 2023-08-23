package com.example.bff.api.operations.cartitem.removeitem;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartRepo {
    private UUID id;
    private UUID targetItemId;
    private BigDecimal price;
    private Integer quantity;
    private List<UUID> reviews;
}
