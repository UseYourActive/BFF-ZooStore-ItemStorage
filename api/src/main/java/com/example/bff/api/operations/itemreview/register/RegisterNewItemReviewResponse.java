package com.example.bff.api.operations.itemreview.register;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterNewItemReviewResponse implements OperationResult {
    private String id;
    private String comment;
    private String commentingUserId;
    private String itemId;
    private Integer rating;
}
