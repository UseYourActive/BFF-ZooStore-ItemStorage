package com.example.bff.api.operations.itemreview.register;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class RegisterNewItemReviewRequest implements OperationInput {
    private final String comment;
    private final String commentingUserId;
    private final String itemId;
    private final Integer rating;
}
