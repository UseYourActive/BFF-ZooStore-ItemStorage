package com.example.bff.api.operations.auth.changepassword;

import com.example.bff.api.base.OperationInput;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserChangePasswordRequest implements OperationInput {

    @NotEmpty(message = "New password is required!")
    private String password;

    private String email;
}
