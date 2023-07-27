package com.example.bff.core.processors;


import com.example.bff.api.operations.findall.FindAllItemsOperation;
import com.example.bff.api.operations.findall.FindAllItemsRequest;
import com.example.bff.api.operations.findall.FindAllItemsResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.restexport.StorageRestClient;
import com.example.zoostore.api.operations.item.findall.FindAllItemsResult;
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
        FindAllItemsResult allItems;

        try {
            allItems = zooStoreRestClient.getAllItems(findAllItemsRequest.getIncludeArchived(),findAllItemsRequest.getPageNumber(), findAllItemsRequest.getNumberOfItemsPerPage(), findAllItemsRequest.getTagId());
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        try {
            //storageRestClient.getAllItems();
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        return FindAllItemsResponse.builder()
                .build();
    }
}
