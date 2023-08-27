package com.example.bff.api.operations.cartitem.find.findall;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllCartItemsInRepo {
    private String id;
    private String targetItemId;
    private BigDecimal price;
    private Integer quantity;
    private List<String> reviews;
}
