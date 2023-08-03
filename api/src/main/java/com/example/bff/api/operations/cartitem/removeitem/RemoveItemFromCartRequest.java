package com.example.bff.api.operations.cartitem.removeitem;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartRequest implements OperationInput {
    private UUID targetItemId;
}
