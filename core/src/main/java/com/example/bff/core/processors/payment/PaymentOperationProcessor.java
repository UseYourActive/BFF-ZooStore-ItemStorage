package com.example.bff.core.processors.payment;

import com.example.bff.api.operations.payment.PaymentOperation;
import com.example.bff.api.operations.payment.PaymentRequest;
import com.example.bff.api.operations.payment.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.bff.core.config.PaymentLoggerMessages.PAYMENT_REQUEST_PROCESSED_SUCCESSFULLY;
import static com.example.bff.core.config.PaymentLoggerMessages.PROCESSING_PAYMENT_REQUEST;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentOperationProcessor implements PaymentOperation {
    @Override
    public PaymentResponse process(PaymentRequest paymentRequest) {
        log.info(PROCESSING_PAYMENT_REQUEST);

        log.info(PAYMENT_REQUEST_PROCESSED_SUCCESSFULLY);
        return PaymentResponse.builder()
                .isSuccessful(true)
                .build();
    }
}
