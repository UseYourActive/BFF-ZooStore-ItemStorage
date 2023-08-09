package com.example.bff.rest.controllers;

import com.example.bff.api.operations.auth.login.LoginUserRequest;
import com.example.bff.api.operations.auth.login.LoginUserResponse;
import com.example.bff.api.operations.auth.register.RegisterUserRequest;
import com.example.bff.api.operations.auth.register.RegisterUserResponse;
import com.example.bff.rest.security.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody @Valid RegisterUserRequest request){
        return new ResponseEntity<>(authenticationService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid LoginUserRequest request) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", result.getJwt());
        return new ResponseEntity<>(authenticationService.login(request), HttpStatus.OK);
    }
}
