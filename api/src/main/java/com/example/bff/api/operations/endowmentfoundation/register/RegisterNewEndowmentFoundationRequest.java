package com.example.bff.api.operations.endowmentfoundation.register;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterNewEndowmentFoundationRequest implements OperationInput {
    private String name;
    private String donationAccount;
    private String address;
}
