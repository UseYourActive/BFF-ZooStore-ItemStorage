package com.example.bff.api.operations.cartitem.find.byid;

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
    private String id;
    private String productName;
    private String description;
    private Boolean isArchived;
    private String vendorId;
    private List<String> tagIds;
    private List<String> multimediaIds;
    private Integer quantity;
    private BigDecimal price;
}
