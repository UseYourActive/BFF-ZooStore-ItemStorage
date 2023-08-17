package com.example.bff.api.operations.payment;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentRequest implements OperationInput {
    private String creditCardNumber;
    private String email;
}
