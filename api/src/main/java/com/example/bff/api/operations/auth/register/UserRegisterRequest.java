package com.example.bff.api.operations.auth.register;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UserRegisterRequest implements OperationInput {
    //@Email
    private final String email;

    @NotEmpty
    private final String password;

    @NotEmpty
    private final String phoneNumber;

    @NotEmpty
    private final String firstName;

    @NotEmpty
    private final String lastName;
}
