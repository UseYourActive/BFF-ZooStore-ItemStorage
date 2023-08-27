package com.example.bff.api.operations.cartitem.addreview;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddReviewToCartItemRequest implements OperationInput {
    private String userId;
    private String productId;
    private String commentId;
}
