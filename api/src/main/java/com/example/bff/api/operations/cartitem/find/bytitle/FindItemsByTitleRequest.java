package com.example.bff.api.operations.cartitem.find.bytitle;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class FindItemsByTitleRequest implements OperationInput {
    private final String title;
    private final Integer pageNumber;
    private final Integer pageSize;
}