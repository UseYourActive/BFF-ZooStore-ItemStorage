package com.example.bff.api.operations.payment;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class PaymentRequest implements OperationInput {
    private final String creditCardNumber;
    private final String email;
}
