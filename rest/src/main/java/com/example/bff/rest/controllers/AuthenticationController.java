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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @ApiResponse(responseCode = "201", description = "User registered successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid field contents.")
    @Operation(description = "Registers a new user.",
            summary = "Registers a new user.")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse registeredUserResponse = this.userRegisterOperation.process(userRegisterRequest);

        return ResponseEntity.status(201).body(registeredUserResponse);
    }

    @PostMapping("/login")
    @ApiResponse(responseCode = "200", description = "Login successful.")
    @ApiResponse(responseCode = "400", description = "Invalid field contents.")
    @ApiResponse(responseCode = "403", description = "Invalid credentials.")
    @Operation(description = "Checks credentials and returns JWT in response header.",
            summary = "Login with email and password.")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserLoginResponse result = this.userLoginOperation.process(userLoginRequest);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getJwt());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @ApiResponse(responseCode = "200",description = "Password changed successfully.")
    @ApiResponse(responseCode = "400",description = "Current or new password is empty.")
    @ApiResponse(responseCode = "403",description = "Current password is invalid or token is blacklisted.")
    @Operation(description = "Changes the password of the currently logged user and ends current session.",
            summary = "Changes current password and invalidates current jwt.")
    @PatchMapping("/change-password")
    public ResponseEntity<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserChangePasswordRequest userChangePasswordRequest) {
        return new ResponseEntity<>(this.userChangePasswordOperation.process(userChangePasswordRequest), HttpStatus.OK);
    }
}
