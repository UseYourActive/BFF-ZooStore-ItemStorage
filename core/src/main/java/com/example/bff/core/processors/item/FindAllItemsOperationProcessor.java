package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.find.all.FindAllItemsOperation;
import com.example.bff.api.operations.item.find.all.FindAllItemsRequest;
import com.example.bff.api.operations.item.find.all.FindAllItemsResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.api.operations.storageitem.find.all.FindAllStorageItemResponse;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllItemsOperationProcessor implements FindAllItemsOperation {
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;

    @Override
    public FindAllItemsResponse process(final FindAllItemsRequest findAllItemsRequest) {
        com.example.zoostore.api.operations.item.find.all.FindAllItemsResponse allItems;
        FindAllStorageItemResponse allStorageItems;

        try {
            allItems = zooStoreRestClient.getAllItems(findAllItemsRequest.getIncludeArchived(),
                    findAllItemsRequest.getPageNumber(),
                    findAllItemsRequest.getNumberOfItemsPerPage(),
                    findAllItemsRequest.getTagId());

            allStorageItems = storageRestClient.findAllStorageItems();
        } catch (Exception e) {
            throw new ItemNotFoundException();
        }

        return FindAllItemsResponse.builder()

                .build();
    }
}
