package com.example.bff.rest.controllers;

import com.example.bff.api.operations.auth.changepassword.UserChangePasswordOperation;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordRequest;
import com.example.bff.api.operations.auth.changepassword.UserChangePasswordResponse;
import com.example.bff.api.operations.auth.login.UserLoginOperation;
import com.example.bff.api.operations.auth.login.UserLoginRequest;
import com.example.bff.api.operations.auth.login.UserLoginResponse;
import com.example.bff.api.operations.auth.register.UserRegisterOperation;
import com.example.bff.api.operations.auth.register.UserRegisterRequest;
import com.example.bff.api.operations.auth.register.UserRegisterResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserRegisterOperation userRegisterOperation;
    private final UserLoginOperation userLoginOperation;
    private final UserChangePasswordOperation userChangePasswordOperation;

    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse registeredUserResponse = this.userRegisterOperation.process(userRegisterRequest);

        return ResponseEntity.status(201).body(registeredUserResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        return new ResponseEntity<>(userLoginOperation.process(userLoginRequest), HttpStatus.OK);
    }

    @PutMapping("/change-password")
    public ResponseEntity<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserChangePasswordRequest userChangePasswordRequest) {
        return new ResponseEntity<>(this.userChangePasswordOperation.process(userChangePasswordRequest), HttpStatus.OK);
    }
}
