package com.example.bff.core.processors.item.findall;

import com.example.bff.api.operations.item.findall.FindAllItemsOperation;
import com.example.bff.api.operations.item.findall.FindAllItemsRequest;
import com.example.bff.api.operations.item.findall.FindAllItemsResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.api.operations.findall.FindAllStorageItemResponse;
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
    public FindAllItemsResponse process(FindAllItemsRequest findAllItemsRequest) {
        com.example.zoostore.api.operations.item.findall.FindAllItemsResponse allItems;
        FindAllStorageItemResponse allStorageItems;

        try {
            allItems = zooStoreRestClient.getAllItems(findAllItemsRequest.getIncludeArchived(),
                    findAllItemsRequest.getPageNumber(),
                    findAllItemsRequest.getNumberOfItemsPerPage(),
                    findAllItemsRequest.getTagId());
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        try {
            allStorageItems = storageRestClient.findAllStorageItems();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        return FindAllItemsResponse.builder()
                .build();
    }
}
