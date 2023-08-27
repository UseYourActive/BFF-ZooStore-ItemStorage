package com.example.bff.api.operations.itemreview;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterNewItemReviewRequest implements OperationInput {
    private String comment;
    private String commentingUserId;
    private String itemId;
    private Integer rating;
}
