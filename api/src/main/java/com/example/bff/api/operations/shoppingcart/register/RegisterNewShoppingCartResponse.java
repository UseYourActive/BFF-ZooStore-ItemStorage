package com.example.bff.api.operations.shoppingcart.register;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterNewShoppingCartResponse implements OperationResult {
    private UUID cartId;
    private UUID userId;
    private List<UUID> items;
    private Integer quantity;
}
