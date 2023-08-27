package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.add.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.add.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.add.AddItemToCartResponse;
import com.example.bff.api.operations.cartitem.empty.EmptyCartOperation;
import com.example.bff.api.operations.cartitem.empty.EmptyCartRequest;
import com.example.bff.api.operations.cartitem.empty.EmptyCartResponse;
import com.example.bff.api.operations.cartitem.find.all.FindAllItemsOperation;
import com.example.bff.api.operations.cartitem.find.byid.FindItemByIdOperation;
import com.example.bff.api.operations.cartitem.find.bytag.FindItemByTagOperation;
import com.example.bff.api.operations.cartitem.find.bytitle.FindItemsByTitleOperation;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsResponse;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Validated
@RequestMapping("/cart-item")
@RestController
public class CartItemController {
    private final AddItemToCartOperation addItemToCartOperation;
    private final RemoveItemFromCartOperation removeItemFromCartOperation;
    private final FindAllCartItemsOperation findAllCartItemsOperation;
    private final FindItemByTagOperation findItemByTagOperation;
    private final FindAllItemsOperation findAllItemsOperation;
    private final FindItemsByTitleOperation findItemsByTitleOperation;
    private final FindItemByIdOperation findItemByIdOperation;
    private final EmptyCartOperation emptyCartOperation;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully return a list of items."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Returns back a list of all cart items.",
            summary = "Returns a list of cart items.")
    @GetMapping(path = "/")
    public ResponseEntity<FindAllCartItemsResponse> listAllItemsInCart(@RequestBody @Valid FindAllCartItemsRequest request){
        return new ResponseEntity<>(findAllCartItemsOperation.process(request), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Added an item to shopping cart."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Adds an item to the shopping cart.",
            summary = "Add item to shopping cart.")
    @PostMapping(path = "/add")
    public ResponseEntity<AddItemToCartResponse> addItemToCart(@RequestBody @Valid AddItemToCartRequest request){
        return new ResponseEntity<>(addItemToCartOperation.process(request), HttpStatus.CREATED);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully removed an item from shopping cart."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Removes an item from the shopping cart.",
            summary = "Remove an item from cart.")
    @DeleteMapping(path = "/remove")
    public ResponseEntity<RemoveItemFromCartResponse> removeItemFromCart(@RequestBody @Valid RemoveItemFromCartRequest request){
        return new ResponseEntity<>(removeItemFromCartOperation.process(request), HttpStatus.OK);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully emptied the shopping cart."),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = "text/html"))
    })
    @Operation(description = "Removes every item from the currently logged users shopping cart.",
            summary = "Empties the cart.")
    @DeleteMapping(path = "/empty")
    public ResponseEntity<EmptyCartResponse> emptyItemCart(@RequestBody @Valid EmptyCartRequest request){
        return new ResponseEntity<>(emptyCartOperation.process(request), HttpStatus.OK);
    }

}
