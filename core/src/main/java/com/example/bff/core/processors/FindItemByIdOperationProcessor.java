package com.example.bff.core.processors;

import com.example.bff.api.operations.FindItemByIdOperation;
import com.example.bff.api.operations.FindItemByIdRequest;
import com.example.bff.api.operations.FindItemByIdResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindItemByIdOperationProcessor implements FindItemByIdOperation{
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;

    @Override
    public FindItemByIdResponse process(FindItemByIdRequest findItemByIdRequest) {
        com.example.zoostore.api.operations.item.findbyid.FindItemByIdResponse itemFoundByIdInZooStore;
        com.example.storage.api.operations.findbyid.FindItemByIdResponse itemFoundByIdInStorage;

        try {
            itemFoundByIdInZooStore = zooStoreRestClient.getItemById(String.valueOf(findItemByIdRequest.getItemId()));
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        try {
            itemFoundByIdInStorage = storageRestClient.getItemById(String.valueOf(findItemByIdRequest.getItemId()));
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        if(itemFoundByIdInZooStore.getMultimediaIds().isEmpty() || itemFoundByIdInZooStore.getMultimediaIds() == null){
            return FindItemByIdResponse.builder()
                    .id(itemFoundByIdInZooStore.getItemId())
                    .description(itemFoundByIdInZooStore.getDescription())
                    .isArchived(itemFoundByIdInZooStore.isArchived())
                    .vendorId(itemFoundByIdInZooStore.getVendorId())
                    .tagIds(itemFoundByIdInZooStore.getTagIds())
                    .productName(itemFoundByIdInZooStore.getProductName())
                    .price(itemFoundByIdInStorage.getPrice())
                    .quantity(itemFoundByIdInStorage.getQuantity())
                    .build();
        }

        return FindItemByIdResponse.builder()
                .id(itemFoundByIdInZooStore.getItemId())
                .description(itemFoundByIdInZooStore.getDescription())
                .isArchived(itemFoundByIdInZooStore.isArchived())
                .vendorId(itemFoundByIdInZooStore.getVendorId())
                .tagIds(itemFoundByIdInZooStore.getTagIds())
                .productName(itemFoundByIdInZooStore.getProductName())
                .multimediaIds(itemFoundByIdInZooStore.getMultimediaIds())
                .price(itemFoundByIdInStorage.getPrice())
                .quantity(itemFoundByIdInStorage.getQuantity())
                .build();
    }
}
