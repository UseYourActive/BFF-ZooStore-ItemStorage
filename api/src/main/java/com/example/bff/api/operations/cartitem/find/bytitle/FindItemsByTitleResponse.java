package com.example.bff.api.operations.cartitem.find.bytitle;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class FindItemsByTitleResponse implements OperationResult {
    private Integer page;
    private Long itemCount;
    private List<ItemsFoundAmountAndPriceData> items;
}