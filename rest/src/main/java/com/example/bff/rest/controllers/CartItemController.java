package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
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

    @GetMapping()
    public ResponseEntity<FindAllCartItemsResponse> listAllItemsInCart(FindAllCartItemsRequest request){
        return new ResponseEntity<>(findAllCartItemsOperation.process(request), HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<AddItemToCartResponse> addItemToCart(AddItemToCartRequest request){
        return new ResponseEntity<>(addItemToCartOperation.process(request), HttpStatus.CREATED);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(RemoveItemFromCartRequest request){
        return new ResponseEntity<>(removeItemFromCartOperation.process(request), HttpStatus.OK);
    }
}
