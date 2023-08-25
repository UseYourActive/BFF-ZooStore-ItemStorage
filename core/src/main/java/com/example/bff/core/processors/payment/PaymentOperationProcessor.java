package com.example.bff.core.processors.payment;

import com.example.bff.api.operations.payment.PaymentOperation;
import com.example.bff.api.operations.payment.PaymentRequest;
import com.example.bff.api.operations.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentOperationProcessor implements PaymentOperation {
    @Override
    public PaymentResponse process(PaymentRequest paymentRequest) {
        log.info("Processing payment request");

        log.info("Payment request processed successfully");
        return PaymentResponse.builder()
                .isSuccessful(true)
                .build();
    }
}
