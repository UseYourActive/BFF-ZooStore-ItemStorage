package com.example.bff.api.operations.item.addreview;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddReviewToCartItemRequest implements OperationInput {
    private UUID userId;
    private UUID productId;
    private UUID commentId;
}
