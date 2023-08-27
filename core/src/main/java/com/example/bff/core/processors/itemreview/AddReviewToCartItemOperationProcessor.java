package com.example.bff.core.processors.itemreview;

import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemOperation;
import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemRequest;
import com.example.bff.api.operations.cartitem.addreview.AddReviewToCartItemResponse;
import com.example.bff.core.exceptions.ItemReviewNotFoundException;
import com.example.bff.core.exceptions.ProductNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddReviewToCartItemOperationProcessor implements AddReviewToCartItemOperation {
    private final CartItemRepository cartItemRepository;
    private final ItemReviewRepository itemReviewRepository;

    @Override
    public AddReviewToCartItemResponse process(final AddReviewToCartItemRequest addReviewToCartItemRequest) {
        log.info("Starting add review to cart item operation");

        CartItem cartItem = cartItemRepository.findById(UUID.fromString(addReviewToCartItemRequest.getProductId()))
                .orElseThrow(ProductNotFoundException::new);
        log.info("Cart Item has successfully been found in the database with id = {}", cartItem.getTargetItemId());

        ItemReview itemReview = itemReviewRepository.findById(UUID.fromString(addReviewToCartItemRequest.getCommentId()))
                .orElseThrow(ItemReviewNotFoundException::new);
        log.info("Item Review has successfully been found in the database with id = {}", itemReview.getId());

        cartItem.getReviews().add(itemReview);

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        log.info("Review added to cart item with id = {}", savedCartItem.getId());

        List<UUID> collect = savedCartItem.getReviews().stream()
                .map(ItemReview::getId)
                .toList();

        AddReviewToCartItemResponse response = AddReviewToCartItemResponse.builder()
                .id(String.valueOf(savedCartItem.getId()))
                .price(savedCartItem.getPrice())
                .quantity(savedCartItem.getQuantity())
                .targetItem(String.valueOf(savedCartItem.getTargetItemId()))
                .reviews(collect.stream()
                        .map(UUID::toString)
                        .toList())
                .build();
        log.info("Add review to cart item operation completed");

        return response;
    }
}
