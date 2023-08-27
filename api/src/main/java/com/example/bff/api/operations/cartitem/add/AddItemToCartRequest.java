package com.example.bff.api.operations.cartitem.add;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddItemToCartRequest implements OperationInput {
    private String itemId;

    @Positive
    private Integer quantity;
}
