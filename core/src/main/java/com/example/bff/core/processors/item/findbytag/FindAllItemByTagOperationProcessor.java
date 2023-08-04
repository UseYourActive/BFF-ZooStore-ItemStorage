package com.example.bff.core.processors.item.findbytag;

import com.example.bff.api.operations.item.findbytag.FindItemByTagOperation;
import com.example.bff.api.operations.item.findbytag.FindAllItemsByTagRequest;
import com.example.bff.api.operations.item.findbytag.FindAllItemsByTagResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllItemByTagOperationProcessor implements FindItemByTagOperation {
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;

    @Override
    public FindAllItemsByTagResponse process(FindAllItemsByTagRequest findItemByTagRequest) {
//        com.example.zoostore.api.operations.item.findbytag.FindAllItemsByTagResponse itemsWithTag;
//
//        try {
//            itemsWithTag = zooStoreRestClient.findItemsWithTag(String.valueOf(findItemByTagRequest.getTagId()),
//                    findItemByTagRequest.getPageNumber(),
//                    findItemByTagRequest.getNumberOfItemsPerPage());
//
//
//        }catch (Exception e){
//            throw new ItemNotFoundException();
//        }
//
//        return FindAllItemsByTagResponse.builder()
//
//                .build();

        return null;
    }
}
