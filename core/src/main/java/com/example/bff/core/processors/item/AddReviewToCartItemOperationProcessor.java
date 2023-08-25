package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.addreview.AddReviewToCartItemOperation;
import com.example.bff.api.operations.item.addreview.AddReviewToCartItemRequest;
import com.example.bff.api.operations.item.addreview.AddReviewToCartItemResponse;
import com.example.bff.core.exceptions.ItemReviewNotFoundException;
import com.example.bff.core.exceptions.ProductNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddReviewToCartItemOperationProcessor implements AddReviewToCartItemOperation {
    private final CartItemRepository cartItemRepository;
    private final ItemReviewRepository itemReviewRepository;

    @Override
    public AddReviewToCartItemResponse process(AddReviewToCartItemRequest addReviewToCartItemRequest) {
        log.info("Starting add review to cart item operation");

        CartItem cartItem = cartItemRepository.findById(addReviewToCartItemRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        log.info("Cart Item has successfully been found in the database with id = {}", cartItem.getTargetItemId());

        ItemReview itemReview = itemReviewRepository.findById(addReviewToCartItemRequest.getCommentId())
                .orElseThrow(ItemReviewNotFoundException::new);
        log.info("Item Review has successfully been found in the database with id = {}", itemReview.getId());

        cartItem.getReviews().add(itemReview);

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        log.info("Review added to cart item with id = {}", savedCartItem.getId());

        AddReviewToCartItemResponse response = AddReviewToCartItemResponse.builder()
                .id(savedCartItem.getId())
                .price(savedCartItem.getPrice())
                .quantity(savedCartItem.getQuantity())
                .targetItem(savedCartItem.getTargetItemId())
                .reviews(savedCartItem.getReviews().stream()
                        .map(ItemReview::getId)
                        .collect(Collectors.toList()))
                .build();
        log.info("Add review to cart item operation completed");

        return response;
    }
}
