package com.example.bff.api.operations.cartitem.removeitem;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartResponse implements OperationResult {
    private Set<RemoveItemFromCartRepo> itemFromCartRepo;
}
