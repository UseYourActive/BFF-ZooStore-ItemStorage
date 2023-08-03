package com.example.bff.api.operations.cartitem.additem;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddItemToCartRequest implements OperationInput {
    private UUID itemId;

    @Positive
    private Integer quantity;
}
