package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.add.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.add.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.add.AddItemToCartResponse;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartOperation;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartRequest;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartResponse;
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
//@Validated
@RequestMapping("/shopping-cart")
@RestController
public class ShoppingCartController {
    private final AddItemToCartOperation addItemToCartOperation;
    private final RegisterNewShoppingCartOperation registerNewShoppingCartOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully registered new shopping cart."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Registers a new shopping cart for the currently logged user in the system.",
            summary = "Registers a new shopping cart.")
    @PostMapping("/register")
    public ResponseEntity<RegisterNewShoppingCartResponse> register(@RequestBody @Valid RegisterNewShoppingCartRequest request){
        return new ResponseEntity<>(registerNewShoppingCartOperation.process(request), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully registered user."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Adds an item to the shopping cart.",
            summary = "Add item to shopping cart.")
    @PatchMapping("/add-cart-item")
    public ResponseEntity<AddItemToCartResponse> addCartItem(@RequestBody @Valid AddItemToCartRequest request){
        return new ResponseEntity<>(addItemToCartOperation.process(request), HttpStatus.OK);
    }
}
