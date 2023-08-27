package com.example.bff.api.operations.cartitem.remove;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RemoveItemFromCartResponse implements OperationResult {
    private List<RemoveItemFromCartRepo> itemFromCartRepo;
}
