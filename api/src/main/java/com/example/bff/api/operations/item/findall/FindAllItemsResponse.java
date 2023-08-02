package com.example.bff.api.operations.item.findall;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllItemsResponse implements OperationResult {
    private UUID id;
    private String productName;
    private String description;
    private boolean isArchived;
    private UUID vendorId;
    private Set<UUID> tagIds;
    private Set<UUID> multimediaIds;
    private Integer quantity;
    private BigDecimal price;

    //private Set<FindAllItemsInRepo> items;
}
