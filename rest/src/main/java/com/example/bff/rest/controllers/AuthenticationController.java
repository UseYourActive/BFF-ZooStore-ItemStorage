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
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final UserRegisterOperation userRegisterOperation;
    private final UserLoginOperation userLoginOperation;
    private final UserChangePasswordOperation userChangePasswordOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered user."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Registers a new user with first name, last name, email, password, phone number",
            summary = "Registers a new user.")
    @PostMapping("/register")
    public ResponseEntity<UserRegisterResponse> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest) {
        UserRegisterResponse registeredUserResponse = this.userRegisterOperation.process(userRegisterRequest);

        return ResponseEntity.status(201).body(registeredUserResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged."),
            @ApiResponse(responseCode = "400", description = "Bad credentials", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "No such username in the database.", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Logins user. Authenticate with email and password.",
            summary = "Logins user.")
    @PostMapping("/login")
    public ResponseEntity<UserLoginResponse> login(@RequestBody @Valid UserLoginRequest userLoginRequest) {
        UserLoginResponse loggedInUserResponse = userLoginOperation.process(userLoginRequest);
        return ResponseEntity.ok(loggedInUserResponse);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully."),
            @ApiResponse(responseCode = "400", description = "Not existing user.", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "400", description = "Password must not be blank.", content = @Content(mediaType = "text/html")),
            @ApiResponse(responseCode = "403", description = "Invalid JWT.", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Changes the password of the currently logged in user.",
            summary = "Change password.")
    @PutMapping("/changePassword")
    public ResponseEntity<UserChangePasswordResponse> changePassword(@RequestBody @Valid UserChangePasswordRequest userChangePasswordRequest) {
        UserChangePasswordResponse userWithChangedPassword = userChangePasswordOperation.process(userChangePasswordRequest);
        return ResponseEntity.ok(userWithChangedPassword);
    }
}
