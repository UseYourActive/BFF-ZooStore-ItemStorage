package com.example.bff.rest.controllers;

import com.example.bff.api.operations.item.find.all.FindAllItemsOperation;
import com.example.bff.api.operations.item.find.all.FindAllItemsRequest;
import com.example.bff.api.operations.item.find.all.FindAllItemsResponse;
import com.example.bff.api.operations.item.find.byid.FindItemByIdOperation;
import com.example.bff.api.operations.item.find.byid.FindItemByIdRequest;
import com.example.bff.api.operations.item.find.byid.FindItemByIdResponse;
import com.example.bff.api.operations.item.find.bytag.FindAllItemsByTagRequest;
import com.example.bff.api.operations.item.find.bytag.FindAllItemsByTagResponse;
import com.example.bff.core.processors.cartitem.FindAllItemByTagOperationProcessor;
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
    private final FindAllItemByTagOperationProcessor findAllItemByTagOperation;

    //@PreAuthorize()
    @GetMapping("/{id}")
    public ResponseEntity<FindItemByIdResponse> findItemById(@PathVariable @org.hibernate.validator.constraints.UUID String id){
        FindItemByIdRequest build = FindItemByIdRequest.builder()
                .itemId(UUID.fromString(id))
                .build();

        return new ResponseEntity<>(findItemByIdOperation.process(build), HttpStatus.OK);
    }

    //@SecurityRequirement(name = "Bearer Authentication")
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

    @GetMapping("/qko")
    public ResponseEntity<FindAllItemsByTagResponse> findItemsByTag(@RequestParam @org.hibernate.validator.constraints.UUID String tagId,
                                                                    @RequestParam Integer pageNumber,
                                                                    @RequestParam Integer numberOfItemsPerPage){
        FindAllItemsByTagRequest build = FindAllItemsByTagRequest.builder()
                .tagId(UUID.fromString(tagId))
                .numberOfItemsPerPage(numberOfItemsPerPage)
                .pageNumber(pageNumber)
                .build();

        return new ResponseEntity<>(findAllItemByTagOperation.process(build), HttpStatus.OK);
    }
}
