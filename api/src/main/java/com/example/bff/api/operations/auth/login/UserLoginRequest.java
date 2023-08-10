package com.example.bff.api.operations.auth.login;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginRequest implements OperationInput{
    //@Email
    private String email;

    @NotEmpty
    private String password;
}
