package com.example.bff.api.operations.cartitem.find.findall;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllCartItemsResponse implements OperationResult {
    private List<FindAllCartItemsInRepo> items;
}
