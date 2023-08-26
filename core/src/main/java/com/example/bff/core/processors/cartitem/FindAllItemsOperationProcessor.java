package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.item.find.all.FindAllItemsOperation;
import com.example.bff.api.operations.item.find.all.FindAllItemsRequest;
import com.example.bff.api.operations.item.find.all.FindAllItemsResponse;
import com.example.storage.api.operations.storageitem.find.all.FindAllStorageItemResponse;
import com.example.storage.restexport.ItemStorageRestExport;
import com.example.zoostore.restexport.ZooStoreRestExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllItemsOperationProcessor implements FindAllItemsOperation {
    private final ZooStoreRestExport zooStoreRestExport;
    private final ItemStorageRestExport itemStorageRestExport;

    @Override
    public FindAllItemsResponse process(final FindAllItemsRequest findAllItemsRequest) {
        com.example.zoostore.api.operations.item.find.all.FindAllItemsResponse allItems;
        FindAllStorageItemResponse allStorageItems;

        allItems = zooStoreRestExport.getAllItems(findAllItemsRequest.getIncludeArchived(),
                findAllItemsRequest.getPageNumber(),
                findAllItemsRequest.getNumberOfItemsPerPage(),
                findAllItemsRequest.getTagId());

        allStorageItems = itemStorageRestExport.findAllStorageItems();

        return FindAllItemsResponse.builder()

                .build();
    }
}
