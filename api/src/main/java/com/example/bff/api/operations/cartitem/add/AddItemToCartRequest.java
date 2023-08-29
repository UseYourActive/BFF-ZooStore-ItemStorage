package com.example.bff.api.operations.cartitem.add;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class AddItemToCartRequest implements OperationInput {
    private final String itemId;

    @Positive
    private final Integer quantity;
}
