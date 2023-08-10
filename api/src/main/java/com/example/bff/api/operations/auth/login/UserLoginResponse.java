package com.example.bff.api.operations.auth.login;

import com.example.bff.api.base.OperationResult;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserLoginResponse implements OperationResult {
    private String jwt;
}
