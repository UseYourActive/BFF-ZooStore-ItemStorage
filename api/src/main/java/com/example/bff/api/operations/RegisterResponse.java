package com.example.bff.api.operations;

import com.example.bff.api.base.OperationResult;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterResponse implements OperationResult {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
