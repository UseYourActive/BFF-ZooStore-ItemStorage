package com.example.bff.api.operations.endowmentfoundation.register;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RegisterNewEndowmentFoundationRequest implements OperationInput {
    private final String name;
    private final String donationAccount;
    private final String address;
}
