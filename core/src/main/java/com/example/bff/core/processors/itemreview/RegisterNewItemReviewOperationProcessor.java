package com.example.bff.core.processors.itemreview;

import com.example.bff.api.operations.itemreview.RegisterNewItemReviewOperation;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewRequest;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewResponse;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterNewItemReviewOperationProcessor implements RegisterNewItemReviewOperation {
    private final ItemReviewRepository itemReviewRepository;

    @Override
    public RegisterNewItemReviewResponse process(final RegisterNewItemReviewRequest registerNewItemReviewRequest) {
        ItemReview itemReview = ItemReview.builder()
                .itemId(registerNewItemReviewRequest.getItemId())
                .comment(registerNewItemReviewRequest.getComment())
                .rating(registerNewItemReviewRequest.getRating())
                //.userId(registerNewItemReviewRequest.getCommentingUserId())
                .build();

        ItemReview save = itemReviewRepository.save(itemReview);

        return RegisterNewItemReviewResponse.builder()
                .itemId(save.getItemId())
                .id(save.getId())
                .comment(save.getComment())
                .rating(save.getRating())
                //.commentingUserId(save.getUserId())
                .build();
    }
}
