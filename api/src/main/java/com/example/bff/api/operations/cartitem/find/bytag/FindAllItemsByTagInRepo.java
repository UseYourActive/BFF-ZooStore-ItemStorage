package com.example.bff.api.operations.cartitem.find.bytag;

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
    private String itemId;
    private String storageItemId;
    private String productName;
    private String description;
    private String vendorId;
    private List<String> multimediaIds;
    private List<String> tagIds;
    private Boolean isArchived;
    private BigDecimal price;
    private Integer quantity;
}
