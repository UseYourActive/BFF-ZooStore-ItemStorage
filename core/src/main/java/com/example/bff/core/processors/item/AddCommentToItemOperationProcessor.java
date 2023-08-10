package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.addcomment.AddCommentToItemOperation;
import com.example.bff.api.operations.item.addcomment.AddCommentToItemRequest;
import com.example.bff.api.operations.item.addcomment.AddCommentToItemResponse;
import com.example.bff.core.exceptions.ItemCommentNotFoundException;
import com.example.bff.core.exceptions.ProductNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddCommentToItemOperationProcessor implements AddCommentToItemOperation {
    private final CartItemRepository cartItemRepository;
    private final ItemReviewRepository itemCommentRepository;

    @Override
    public AddCommentToItemResponse process(AddCommentToItemRequest addCommentToItemRequest) {
        CartItem cartItem = cartItemRepository.findById(addCommentToItemRequest.getProductId())
                .orElseThrow(ProductNotFoundException::new);

        ItemReview itemComment = itemCommentRepository.findById(addCommentToItemRequest.getCommentId())
                .orElseThrow(ItemCommentNotFoundException::new);

        cartItem.getReviews().add(itemComment);

        CartItem save = cartItemRepository.save(cartItem);

        return AddCommentToItemResponse.builder()
                .id(save.getId())
                .user(save.getUser().getId())
                .price(save.getPrice())
                .quantity(save.getQuantity())
                .targetItem(save.getTargetItem())
                .comments(save.getReviews().stream()
                        .map(ItemReview::getId)
                        .toList())
                .build();
    }
}
