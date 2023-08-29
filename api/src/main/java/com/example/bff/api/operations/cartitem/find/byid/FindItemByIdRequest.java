package com.example.bff.api.operations.cartitem.find.byid;


import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class FindItemByIdRequest implements OperationInput {
    private final String itemId;
}
