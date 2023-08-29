package com.example.bff.api.operations.cartitem.find.all;

import com.example.bff.api.base.OperationInput;
import lombok.*;
import org.hibernate.validator.constraints.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@AllArgsConstructor
public class FindAllItemsRequest implements OperationInput {
    private final Boolean includeArchived;
    private final Integer pageNumber;
    private final Integer numberOfItemsPerPage;

    @UUID
    private final String tagId;
}
