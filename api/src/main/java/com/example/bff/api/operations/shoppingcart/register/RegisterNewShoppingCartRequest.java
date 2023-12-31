package com.example.bff.api.operations.shoppingcart.register;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@AllArgsConstructor
public class RegisterNewShoppingCartRequest implements OperationInput {
}
