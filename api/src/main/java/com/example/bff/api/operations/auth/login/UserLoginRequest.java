package com.example.bff.api.operations.auth.login;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class UserLoginRequest implements OperationInput{
    //@Email
    private final String email;

    @NotEmpty
    private final String password;
}
