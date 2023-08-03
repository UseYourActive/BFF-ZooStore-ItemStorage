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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
//@Validated
@RequestMapping("/cart-item")
@RestController
public class CartItemController {
    private final AddItemToCartOperation addItemToCartOperation;
    private final RemoveItemFromCartOperation removeItemFromCartOperation;
    private final FindAllCartItemsOperation findAllCartItemsOperation;
    private final EmptyCartOperation emptyCartOperation;

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
}
