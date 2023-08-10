package com.example.bff.api.operations.auth.changepassword;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserChangePasswordResponse implements OperationResult {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
