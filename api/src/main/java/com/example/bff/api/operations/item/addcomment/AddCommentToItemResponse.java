package com.example.bff.api.operations.item.addcomment;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AddCommentToItemResponse implements OperationResult {
    private UUID id;
    private UUID targetItem;
    private Integer quantity;
    private BigDecimal price;
    private UUID user;
    private List<UUID> comments;
}
