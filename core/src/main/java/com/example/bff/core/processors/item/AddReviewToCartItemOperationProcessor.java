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
import static com.example.bff.core.config.ItemReviewLoggerMessages.ITEM_REVIEW_FOUND_IN_DATABASE_WITH_ID;

@RequiredArgsConstructor
@Service
@Slf4j
public class AddReviewToCartItemOperationProcessor implements AddReviewToCartItemOperation {
    private final CartItemRepository cartItemRepository;
    private final ItemReviewRepository itemCommentRepository;

    @Override
    public AddReviewToCartItemResponse process(AddReviewToCartItemRequest addReviewToCartItemRequest) {
        CartItem cartItem = cartItemRepository.findById(addReviewToCartItemRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        log.info(CART_ITEM_FOUND_IN_DATABASE_WITH_ID, cartItem.getTargetItemId());

        ItemReview itemReview = itemCommentRepository.findById(addReviewToCartItemRequest.getCommentId())
                .orElseThrow(ItemCommentNotFoundException::new);

        log.info(ITEM_REVIEW_FOUND_IN_DATABASE_WITH_ID, itemReview.getId());

        cartItem.getReviews().add(itemReview);

        CartItem save = cartItemRepository.save(cartItem);

        return AddReviewToCartItemResponse.builder()
                .id(save.getId())
                .price(save.getPrice())
                .quantity(save.getQuantity())
                .targetItem(save.getTargetItemId())
                .reviews(save.getReviews().stream()
                        .map(ItemReview::getId)
                        .collect(Collectors.toList()))
                .build();
    }
}
