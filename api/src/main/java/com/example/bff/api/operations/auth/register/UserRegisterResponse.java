package com.example.bff.api.operations.auth.register;

import com.example.bff.api.base.OperationResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class UserRegisterResponse implements OperationResult {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @JsonIgnore // doesn't serialize
    private String jwt;
}
