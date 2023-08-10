package com.example.bff.api.operations.item.addcomment;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddCommentToItemRequest implements OperationInput {
    private UUID userId;
    private UUID productId;
    private UUID commentId;
}
