package com.example.bff.rest.controllers;

import com.example.bff.api.operations.findall.FindAllItemsOperation;
import com.example.bff.api.operations.findall.FindAllItemsRequest;
import com.example.bff.api.operations.findall.FindAllItemsResponse;
import com.example.bff.api.operations.findbyid.FindItemByIdOperation;
import com.example.bff.api.operations.findbyid.FindItemByIdRequest;
import com.example.bff.api.operations.findbyid.FindItemByIdResponse;
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
    private final FindAllItemsOperation findAllItemsOperation;

    //@PreAuthorize()
    @GetMapping("/find/{id}")
    public ResponseEntity<FindItemByIdResponse> findItemById(@PathVariable @org.hibernate.validator.constraints.UUID String id){
        FindItemByIdRequest build = FindItemByIdRequest.builder()
                .itemId(UUID.fromString(id))
                .build();

        return new ResponseEntity<>(findItemByIdOperation.process(build), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<FindAllItemsResponse> findItemById(@RequestParam Boolean includeArchived,
                                                             @RequestParam Integer pageNumber,
                                                             @RequestParam Integer numberOfItemsPerPage,
                                                             @RequestParam @org.hibernate.validator.constraints.UUID String tagId){
        FindAllItemsRequest build = FindAllItemsRequest.builder()
                .includeArchived(includeArchived)
                .numberOfItemsPerPage(numberOfItemsPerPage)
                .pageNumber(pageNumber)
                .tagId(tagId)
                .build();

        return new ResponseEntity<>(findAllItemsOperation.process(build), HttpStatus.OK);
    }
}
