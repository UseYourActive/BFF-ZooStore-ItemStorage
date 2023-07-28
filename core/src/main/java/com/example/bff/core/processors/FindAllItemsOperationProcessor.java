package com.example.bff.core.processors;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllItemsOperationProcessor { //implements FindAllItemsOperation
    //private final ZooStoreRestClient zooStoreRestClient;
    //private final StorageRestClient storageRestClient;


//    @Override
//    public FindAllItemsResponse process(FindAllItemsRequest findAllItemsRequest) {
//        FindAllItemsResult allItems;
//
//        try {
//            allItems = zooStoreRestClient.getAllItems(findAllItemsRequest.getIncludeArchived(),findAllItemsRequest.getPageNumber(), findAllItemsRequest.getNumberOfItemsPerPage(), findAllItemsRequest.getTagId());
//        }catch (Exception e){
//            throw new ItemNotFoundException();
//        }
//
//        try {
//            //storageRestClient.getAllItems();
//        }catch (Exception e){
//            throw new ItemNotFoundException();
//        }
//
//        return FindAllItemsResponse.builder()
//                .build();
//    }
}
