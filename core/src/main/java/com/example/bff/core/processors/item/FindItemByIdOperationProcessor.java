package com.example.bff.core.processors.item;

import com.example.bff.api.operations.item.find.byid.FindItemByIdOperation;
import com.example.bff.api.operations.item.find.byid.FindItemByIdRequest;
import com.example.bff.api.operations.item.find.byid.FindItemByIdResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.storage.restexport.ItemStorageRestExport;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.restexport.ZooStoreRestClient;
import com.example.zoostore.restexport.ZooStoreRestExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindItemByIdOperationProcessor implements FindItemByIdOperation{
    private final ItemStorageRestExport storageRestExport;
    private final ZooStoreRestExport zooStoreRestExport;
    @Override
    public FindItemByIdResponse process(final FindItemByIdRequest findItemByIdRequest) {
        com.example.zoostore.api.operations.item.find.byid.FindItemByIdResponse itemFoundByIdInZooStore;
        com.example.storage.api.operations.storageitem.find.byid.FindItemByIdResponse itemFoundByIdInStorage;

        itemFoundByIdInZooStore = zooStoreRestExport.getItemById(String.valueOf(findItemByIdRequest.getItemId()));

        itemFoundByIdInStorage = storageRestExport.findItemById(String.valueOf(findItemByIdRequest.getItemId()));

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
