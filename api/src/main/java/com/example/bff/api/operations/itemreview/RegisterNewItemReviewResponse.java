package com.example.bff.api.operations.itemreview;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RegisterNewItemReviewResponse implements OperationResult {
    private UUID id;
    private String comment;
    private UUID commentingUserId;
    private UUID itemId;
    private Integer rating;
}
