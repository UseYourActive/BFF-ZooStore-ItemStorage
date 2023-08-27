package com.example.bff.api.operations.cartitem.find.bytitle;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
public class FindItemsByTitleRequest implements OperationInput {
    private String title;
    private Integer pageNumber;
    private Integer pageSize;
}