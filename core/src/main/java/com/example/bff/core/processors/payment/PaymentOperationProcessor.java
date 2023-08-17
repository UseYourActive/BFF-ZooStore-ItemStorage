package com.example.bff.core.processors.payment;

import com.example.bff.api.operations.payment.PaymentOperation;
import com.example.bff.api.operations.payment.PaymentRequest;
import com.example.bff.api.operations.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PaymentOperationProcessor implements PaymentOperation {
    @Override
    public PaymentResponse process(PaymentRequest paymentRequest) {
        return PaymentResponse.builder()
                .isSuccessful(true)
                .build();
    }
}