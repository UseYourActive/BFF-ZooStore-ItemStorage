package com.example.bff.api.operations.auth.register;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterUserResponse implements OperationResult {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String jwt;
}
