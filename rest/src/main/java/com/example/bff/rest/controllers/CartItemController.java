package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.api.operations.cartitem.empty.EmptyCartOperation;
import com.example.bff.api.operations.cartitem.empty.EmptyCartRequest;
import com.example.bff.api.operations.cartitem.empty.EmptyCartResponse;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.api.operations.item.find.all.FindAllItemsOperation;
import com.example.bff.api.operations.item.find.all.FindAllItemsRequest;
import com.example.bff.api.operations.item.find.all.FindAllItemsResponse;
import com.example.bff.api.operations.item.find.byid.FindItemByIdOperation;
import com.example.bff.api.operations.item.find.byid.FindItemByIdRequest;
import com.example.bff.api.operations.item.find.byid.FindItemByIdResponse;
import com.example.bff.api.operations.item.find.bytag.FindAllItemsByTagRequest;
import com.example.bff.api.operations.item.find.bytag.FindAllItemsByTagResponse;
import com.example.bff.core.processors.cartitem.FindAllItemByTagOperationProcessor;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@Validated
@RequestMapping("/cart-item")
@RestController
public class CartItemController {
    private final AddItemToCartOperation addItemToCartOperation;
    private final RemoveItemFromCartOperation removeItemFromCartOperation;
    private final FindAllCartItemsOperation findAllCartItemsOperation;
    private final EmptyCartOperation emptyCartOperation;

//    private final FindItemByIdOperation findItemByIdOperation;
//    private final FindAllItemsOperation findAllItemsOperation;
//    private final FindAllItemByTagOperationProcessor findAllItemByTagOperation;

    @GetMapping()
    public ResponseEntity<FindAllCartItemsResponse> listAllItemsInCart(@RequestBody @Valid FindAllCartItemsRequest request){
        return new ResponseEntity<>(findAllCartItemsOperation.process(request), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddItemToCartResponse> addItemToCart(@RequestBody @Valid AddItemToCartRequest request){
        return new ResponseEntity<>(addItemToCartOperation.process(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(@RequestBody @Valid RemoveItemFromCartRequest request){
        return new ResponseEntity<>(removeItemFromCartOperation.process(request), HttpStatus.OK);
    }

    @DeleteMapping("/empty")
    public ResponseEntity<EmptyCartResponse> emptyItemCart(@RequestBody @Valid EmptyCartRequest request){
        return new ResponseEntity<>(emptyCartOperation.process(request), HttpStatus.OK);
    }










//    //@PreAuthorize()
//    @GetMapping("/{id}")
//    public ResponseEntity<FindItemByIdResponse> findItemById(@PathVariable @org.hibernate.validator.constraints.UUID String id){
//        FindItemByIdRequest build = FindItemByIdRequest.builder()
//                .itemId(UUID.fromString(id))
//                .build();
//
//        return new ResponseEntity<>(findItemByIdOperation.process(build), HttpStatus.OK);
//    }
//
//    //@SecurityRequirement(name = "Bearer Authentication")
//    @GetMapping()
//    public ResponseEntity<FindAllItemsResponse> findItemById(@RequestParam Boolean includeArchived,
//                                                             @RequestParam Integer pageNumber,
//                                                             @RequestParam Integer numberOfItemsPerPage,
//                                                             @RequestParam @org.hibernate.validator.constraints.UUID String tagId){
//        FindAllItemsRequest build = FindAllItemsRequest.builder()
//                .includeArchived(includeArchived)
//                .numberOfItemsPerPage(numberOfItemsPerPage)
//                .pageNumber(pageNumber)
//                .tagId(tagId)
//                .build();
//
//        return new ResponseEntity<>(findAllItemsOperation.process(build), HttpStatus.OK);
//    }
//
//    @GetMapping("/qko")
//    public ResponseEntity<FindAllItemsByTagResponse> findItemsByTag(@RequestParam @org.hibernate.validator.constraints.UUID String tagId,
//                                                                    @RequestParam Integer pageNumber,
//                                                                    @RequestParam Integer numberOfItemsPerPage){
//        FindAllItemsByTagRequest build = FindAllItemsByTagRequest.builder()
//                .tagId(UUID.fromString(tagId))
//                .numberOfItemsPerPage(numberOfItemsPerPage)
//                .pageNumber(pageNumber)
//                .build();
//
//        return new ResponseEntity<>(findAllItemByTagOperation.process(build), HttpStatus.OK);
//    }
}
