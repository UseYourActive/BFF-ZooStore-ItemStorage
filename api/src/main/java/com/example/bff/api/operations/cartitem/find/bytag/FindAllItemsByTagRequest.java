package com.example.bff.api.operations.cartitem.find.bytag;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class FindAllItemsByTagRequest implements OperationInput {
    private final String tagId;
    private final Integer pageNumber;
    private final Integer numberOfItemsPerPage;
}
