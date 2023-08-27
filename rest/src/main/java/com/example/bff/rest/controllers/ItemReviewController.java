package com.example.bff.rest.controllers;

import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemOperation;
import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemRequest;
import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemResponse;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewOperation;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewRequest;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
//@Validated
@RequestMapping("/review")
public class ItemReviewController {
    private final AddReviewToCartItemOperation addReviewToCartItemOperation;
    private final RegisterNewItemReviewOperation registerNewItemReviewOperation;

    @PostMapping("/add-to-item")
    public ResponseEntity<AddReviewToCartItemResponse> addToItem(@RequestBody @Valid AddReviewToCartItemRequest request){
        return new ResponseEntity<>(addReviewToCartItemOperation.process(request), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterNewItemReviewResponse> register(@RequestBody @Valid RegisterNewItemReviewRequest request){
        return new ResponseEntity<>(registerNewItemReviewOperation.process(request), HttpStatus.CREATED);
    }
}
