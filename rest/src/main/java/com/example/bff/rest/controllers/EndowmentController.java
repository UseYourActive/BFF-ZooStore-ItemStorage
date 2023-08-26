package com.example.bff.rest.controllers;

import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationResponse;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
//@Validated
@RequestMapping("/endowment")
public class EndowmentController {
    private final DonateToEndowmentFoundationOperation donateToEndowmentFoundationOperation;
    private final RegisterNewEndowmentFoundationOperation registerNewEndowmentFoundationOperation;

    @PostMapping("/register")
    public ResponseEntity<RegisterNewEndowmentFoundationResponse> register(@RequestBody @Valid RegisterNewEndowmentFoundationRequest request){
        return new ResponseEntity<>(registerNewEndowmentFoundationOperation.process(request), HttpStatus.CREATED);
    }

    @PatchMapping("/donate")
    public ResponseEntity<DonateToEndowmentFoundationResponse> donate(@RequestBody @Valid DonateToEndowmentFoundationRequest request){
        return new ResponseEntity<>(donateToEndowmentFoundationOperation.process(request), HttpStatus.OK);
    }
}
