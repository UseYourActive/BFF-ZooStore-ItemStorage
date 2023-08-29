package com.example.bff.core.processors.itemreview;

import com.example.bff.api.operations.itemreview.register.RegisterNewItemReviewOperation;
import com.example.bff.api.operations.itemreview.register.RegisterNewItemReviewRequest;
import com.example.bff.api.operations.itemreview.register.RegisterNewItemReviewResponse;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.ItemReviewRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RegisterNewItemReviewOperationProcessor implements RegisterNewItemReviewOperation {
    private final ItemReviewRepository itemReviewRepository;
    private final UserRepository userRepository;

    @Override
    public RegisterNewItemReviewResponse process(final RegisterNewItemReviewRequest registerNewItemReviewRequest) {
        User user = userRepository.findById(UUID.fromString(registerNewItemReviewRequest.getCommentingUserId()))
                .orElseThrow(UserNotFoundException::new);

        ItemReview itemReview = ItemReview.builder()
                .itemId(UUID.fromString(registerNewItemReviewRequest.getItemId()))
                .comment(registerNewItemReviewRequest.getComment())
                .rating(registerNewItemReviewRequest.getRating())
                .user(user)
                .build();

        ItemReview save = itemReviewRepository.save(itemReview);

        return RegisterNewItemReviewResponse.builder()
                .itemId(String.valueOf(save.getItemId()))
                .id(String.valueOf(save.getId()))
                .comment(save.getComment())
                .rating(save.getRating())
                .commentingUserId(String.valueOf(save.getUser().getId()))
                .build();
    }
}
