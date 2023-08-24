package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.addreview.AddReviewToCartItemOperation;
import com.example.bff.api.operations.item.addreview.AddReviewToCartItemRequest;
import com.example.bff.api.operations.item.addreview.AddReviewToCartItemResponse;
import com.example.bff.core.exceptions.ItemCommentNotFoundException;
import com.example.bff.core.exceptions.ProductNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static com.example.bff.core.config.CartItemLoggerMessages.CART_ITEM_FOUND_IN_DATABASE_WITH_ID;
import static com.example.bff.core.config.CartItemLoggerMessages.STARTING_ADD_REVIEW_TO_CART_ITEM_OPERATION;
import static com.example.bff.core.config.ItemReviewLoggerMessages.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddReviewToCartItemOperationProcessor implements AddReviewToCartItemOperation {
    private final CartItemRepository cartItemRepository;
    private final ItemReviewRepository itemReviewRepository;

    @Override
    public AddReviewToCartItemResponse process(AddReviewToCartItemRequest addReviewToCartItemRequest) {
        log.info(STARTING_ADD_REVIEW_TO_CART_ITEM_OPERATION);

        CartItem cartItem = cartItemRepository.findById(addReviewToCartItemRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);
        log.info(CART_ITEM_FOUND_IN_DATABASE_WITH_ID, cartItem.getTargetItemId());

        ItemReview itemReview = itemReviewRepository.findById(addReviewToCartItemRequest.getCommentId())
                .orElseThrow(ItemCommentNotFoundException::new);
        log.info(ITEM_REVIEW_FOUND_IN_DATABASE_WITH_ID, itemReview.getId());

        cartItem.getReviews().add(itemReview);

        CartItem savedCartItem = cartItemRepository.save(cartItem);
        log.info(REVIEW_ADDED_TO_CART_ITEM_WITH_ID, savedCartItem.getId());

        AddReviewToCartItemResponse response = AddReviewToCartItemResponse.builder()
                .id(savedCartItem.getId())
                .price(savedCartItem.getPrice())
                .quantity(savedCartItem.getQuantity())
                .targetItem(savedCartItem.getTargetItemId())
                .reviews(savedCartItem.getReviews().stream()
                        .map(ItemReview::getId)
                        .collect(Collectors.toList()))
                .build();
        log.info(ADD_REVIEW_TO_CART_ITEM_OPERATION_COMPLETED);

        return response;
    }
}
