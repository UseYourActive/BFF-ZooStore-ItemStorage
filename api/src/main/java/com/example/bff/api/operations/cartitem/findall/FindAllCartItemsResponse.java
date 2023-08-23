package com.example.bff.api.operations.cartitem.findall;

import com.example.bff.api.base.OperationResult;
import lombok.*;

import java.util.List;
import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllCartItemsResponse implements OperationResult {
    private List<FindAllCartItemsInRepo> items;
}
