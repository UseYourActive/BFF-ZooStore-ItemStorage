package com.example.bff.api.operations.cartitem.remove;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RemoveItemFromCartRequest implements OperationInput {
    private final String targetItemId;
}
