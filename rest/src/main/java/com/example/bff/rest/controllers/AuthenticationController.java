package com.example.bff.rest.controllers;

import com.example.bff.api.operations.auth.login.LoginUserOperation;
import com.example.bff.api.operations.auth.login.LoginUserRequest;
import com.example.bff.api.operations.auth.login.LoginUserResponse;
import com.example.bff.api.operations.auth.register.RegisterUserRequest;
import com.example.bff.api.operations.auth.register.RegisterUserResponse;
import com.example.bff.api.operations.auth.register.RegisterUserOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final RegisterUserOperation registrationOperation;
    private final LoginUserOperation loginUserOperation;

    @PostMapping("/register")
    public ResponseEntity<RegisterUserResponse> register(@RequestBody @Valid RegisterUserRequest request){
        return new ResponseEntity<>(registrationOperation.process(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> loginUser(@RequestBody @Valid LoginUserRequest request) {
        LoginUserResponse result = this.loginUserOperation.process(request);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", result.getJwt());
        return new ResponseEntity<>(headers, HttpStatus.OK);
    }

    @GetMapping//TODO
//    @Parameter(name = "Authorization",in = ParameterIn.HEADER,schema  = @Schema(name = "Schema", description = "Schema", example = "Subscription example"))
    public String test(){
        return "Hello auth user.";
    }
}
