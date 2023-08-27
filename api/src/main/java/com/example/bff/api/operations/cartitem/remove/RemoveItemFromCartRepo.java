package com.example.bff.api.operations.cartitem.remove;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartRepo {
    private String id;
    private String targetItemId;
    private BigDecimal price;
    private Integer quantity;
    private List<String> reviews;
}
