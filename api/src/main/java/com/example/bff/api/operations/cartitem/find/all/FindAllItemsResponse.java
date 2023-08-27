package com.example.bff.api.operations.cartitem.find.all;

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
    private String id;
    private String productName;
    private String description;
    private Boolean isArchived;
    private String vendorId;
    private Set<String> tagIds;
    private Set<String> multimediaIds;
    private Integer quantity;
    private BigDecimal price;

    //private Set<FindAllItemsInRepo> items;
}
