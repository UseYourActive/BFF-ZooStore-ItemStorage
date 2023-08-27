package com.example.bff.rest.controllers;

import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationResponse;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered a new Endowment Foundation."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Registers a new endowment foundation in the system.",
            summary = "Registers a new endowment foundation.")
    @PostMapping("/register")
    public ResponseEntity<RegisterNewEndowmentFoundationResponse> register(@RequestBody @Valid RegisterNewEndowmentFoundationRequest request){
        return new ResponseEntity<>(registerNewEndowmentFoundationOperation.process(request), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully donated to a Endowment Foundation."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Donates to an already existing endowment foundation in the system.",
            summary = "Donate to a endowment foundation.")
    @PatchMapping("/donate")
    public ResponseEntity<DonateToEndowmentFoundationResponse> donate(@RequestBody @Valid DonateToEndowmentFoundationRequest request){
        return new ResponseEntity<>(donateToEndowmentFoundationOperation.process(request), HttpStatus.OK);
    }
}
