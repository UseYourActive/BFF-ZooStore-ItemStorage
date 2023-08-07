package com.example.bff.api.operations.item.findbytag;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllItemsByTagInRepo {
    private UUID itemId;
    private UUID storageItemId;
    private String productName;
    private String description;
    private UUID vendorId;
    private List<UUID> multimediaIds;
    private List<UUID> tagIds;
    private Boolean isArchived;
    private BigDecimal price;
    private Integer quantity;
}
