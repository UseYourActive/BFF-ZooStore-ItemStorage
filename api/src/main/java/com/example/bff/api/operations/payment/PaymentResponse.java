package com.example.bff.api.operations.payment;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentResponse implements OperationResult {
    private Boolean isSuccessful;
}
