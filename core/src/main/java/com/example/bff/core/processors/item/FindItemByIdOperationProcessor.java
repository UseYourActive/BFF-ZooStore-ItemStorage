package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.findbyid.FindItemByIdOperation;
import com.example.bff.api.operations.item.findbyid.FindItemByIdRequest;
import com.example.bff.api.operations.item.findbyid.FindItemByIdResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindItemByIdOperationProcessor implements FindItemByIdOperation{
    private final StorageRestClient storageRestClient;
    private final ZooStoreRestClient zooStoreRestClient;
    @Override
    public FindItemByIdResponse process(final FindItemByIdRequest findItemByIdRequest) {
        com.example.zoostore.api.operations.item.find.byid.FindItemByIdResponse itemFoundByIdInZooStore;
        com.example.storage.api.operations.storageitem.find.byid.FindItemByIdResponse itemFoundByIdInStorage;

        try {
            itemFoundByIdInZooStore = zooStoreRestClient.getItemById(String.valueOf(findItemByIdRequest.getItemId()));
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        try {
            itemFoundByIdInStorage = storageRestClient.findItemById(String.valueOf(findItemByIdRequest.getItemId()));
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

        return FindItemByIdResponse.builder()
                .id(itemFoundByIdInZooStore.getItemId())
                .description(itemFoundByIdInZooStore.getDescription())
                .isArchived(itemFoundByIdInZooStore.getIsArchived())
                .vendorId(itemFoundByIdInZooStore.getVendorId())
                .tagIds(itemFoundByIdInZooStore.getTagIds())
                .productName(itemFoundByIdInZooStore.getProductName())
                .multimediaIds(itemFoundByIdInZooStore.getMultimediaIds())
                .price(itemFoundByIdInStorage.getPrice())
                .quantity(itemFoundByIdInStorage.getQuantity())
                .build();
    }
}
