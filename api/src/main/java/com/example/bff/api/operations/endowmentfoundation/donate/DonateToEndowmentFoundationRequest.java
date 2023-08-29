package com.example.bff.api.operations.endowmentfoundation.donate;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class DonateToEndowmentFoundationRequest implements OperationInput {
    private final String foundationId;
    private final BigDecimal amountToDonate;
}
