package com.example.bff.api.operations.endowmentfoundation.donate;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class DonateToEndowmentFoundationResponse implements OperationResult {
    private UUID id;
    private BigDecimal totalAmountOfMoney;
    private String name;
    private String donationAccount;
    private String address;
}
