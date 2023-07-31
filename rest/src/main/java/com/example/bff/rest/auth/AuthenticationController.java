package com.example.bff.rest.auth;

import com.example.bff.api.operations.RegisterRequest;
import com.example.bff.api.operations.RegisterResponse;
import com.example.bff.api.operations.RegistrationOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/authentication")
@RestController
public class AuthenticationController {
    private final RegistrationOperation registrationOperation;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(registrationOperation.process(registerRequest), HttpStatus.CREATED);
    }

}
