package com.example.bff.api.operations.cartitem.addreview;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class AddReviewToCartItemRequest implements OperationInput {
    private final String userId;
    private final String productId;
    private final String commentId;
}
