package com.example.bff.rest.controllers;

import com.example.bff.api.operations.FindItemByIdOperation;
import com.example.bff.api.operations.FindItemByIdRequest;
import com.example.bff.api.operations.FindItemByIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RequestMapping("/item")
@RestController
public class ItemController {
    private final FindItemByIdOperation findItemByIdOperation;

    @GetMapping("/find/{id}")
    public ResponseEntity<FindItemByIdResponse> findItemById(@PathVariable @org.hibernate.validator.constraints.UUID String id){
        return new ResponseEntity<>(findItemByIdOperation.process(FindItemByIdRequest.builder()
                .itemId(UUID.fromString(id))
                .build()), HttpStatus.OK);
    }
}
