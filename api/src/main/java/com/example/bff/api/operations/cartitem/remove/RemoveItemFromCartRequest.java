package com.example.bff.api.operations.cartitem.remove;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartRequest implements OperationInput {
    private String targetItemId;
}
