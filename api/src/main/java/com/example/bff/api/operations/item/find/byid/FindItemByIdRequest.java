package com.example.bff.api.operations.item.find.byid;


import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindItemByIdRequest implements OperationInput {
    private UUID itemId;
}
