package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartOperation;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartRequest;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
//@Validated
@RequestMapping("/shopping-cart")
@RestController
public class ShoppingCartController {
    private final AddItemToCartOperation addItemToCartOperation;
    private final RegisterNewShoppingCartOperation registerNewShoppingCartOperation;

    @PostMapping("/register")
    public ResponseEntity<RegisterNewShoppingCartResponse> register(@RequestBody @Valid RegisterNewShoppingCartRequest request){
        return new ResponseEntity<>(registerNewShoppingCartOperation.process(request), HttpStatus.CREATED);
    }
    @PatchMapping("/add-cart-item")
    public ResponseEntity<AddItemToCartResponse> addCartItem(@RequestBody @Valid AddItemToCartRequest request){
        return new ResponseEntity<>(addItemToCartOperation.process(request), HttpStatus.OK);
    }
}
