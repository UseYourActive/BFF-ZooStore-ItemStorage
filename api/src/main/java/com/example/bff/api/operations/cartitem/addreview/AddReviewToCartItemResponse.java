package com.example.bff.api.operations.cartitem.addreview;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddReviewToCartItemResponse implements OperationResult {
    private String id;
    private String targetItem;
    private Integer quantity;
    private BigDecimal price;
    private List<String> reviews;
}
