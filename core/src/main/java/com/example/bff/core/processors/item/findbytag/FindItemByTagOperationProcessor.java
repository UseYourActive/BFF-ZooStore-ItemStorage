package com.example.bff.core.processors.item.findbytag;

import com.example.bff.api.operations.item.findbytag.FindItemByTagOperation;
import com.example.bff.api.operations.item.findbytag.FindItemByTagRequest;
import com.example.bff.api.operations.item.findbytag.FindItemByTagResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindItemByTagOperationProcessor implements FindItemByTagOperation {
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;

    @Override
    public FindItemByTagResponse process(FindItemByTagRequest findItemByTagRequest) {
        try {
            zooStoreRestClient
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        try {
            storageRestClient
        }catch (Exception e){
            throw new ItemNotFoundException();
        }


    }
}
