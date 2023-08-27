package com.example.bff.api.operations.cartitem.find.bytitle;

import com.example.zoostore.api.operations.item.find.byids.VendorResponse;
import com.example.zoostore.api.operations.item.find.bytag.FindItemsByTagInRepo;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class ItemsFoundAmountAndPriceData {
    private String id;
    private String title;
    private String description;
    private String vendorId;
    private List<String> multimedia;
    private List<String> tags;
    private BigDecimal price;
    private Integer quantity;
}
