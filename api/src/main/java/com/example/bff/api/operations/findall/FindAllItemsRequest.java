package com.example.bff.api.operations.findall;

import com.example.bff.api.base.OperationInput;
import lombok.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllItemsRequest implements OperationInput {
    private Boolean includeArchived;
    private Integer pageNumber;
    private Integer numberOfItemsPerPage;
    private @org.hibernate.validator.constraints.UUID String tagId;
}
