package com.example.bff.api.operations.item.find.bytag;

import com.example.bff.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class FindAllItemsByTagRequest implements OperationInput {
    private UUID tagId;
    private Integer pageNumber;
    private Integer numberOfItemsPerPage;
}
