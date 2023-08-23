package com.example.bff.api.operations.item.find.byid;

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
public class FindItemByIdResponse implements OperationResult {
    private UUID id;
    private String productName;
    private String description;
    private boolean isArchived;
    private UUID vendorId;
    private List<UUID> tagIds;
    private List<UUID> multimediaIds;
    private Integer quantity;
    private BigDecimal price;
}
