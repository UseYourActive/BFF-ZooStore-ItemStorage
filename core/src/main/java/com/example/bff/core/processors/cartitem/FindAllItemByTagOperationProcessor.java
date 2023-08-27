package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.find.bytag.FindAllItemsByTagInRepo;
import com.example.bff.api.operations.cartitem.find.bytag.FindItemByTagOperation;
import com.example.bff.api.operations.cartitem.find.bytag.FindAllItemsByTagRequest;
import com.example.bff.api.operations.cartitem.find.bytag.FindAllItemsByTagResponse;
import com.example.storage.api.operations.storageitem.find.byid.FindItemByIdResponse;
import com.example.storage.restexport.ItemStorageRestExport;
import com.example.zoostore.api.operations.item.find.bytag.FindItemsByTagInRepo;
import com.example.zoostore.api.operations.item.find.bytag.FindItemsByTagResponse;
import com.example.zoostore.restexport.ZooStoreRestExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FindAllItemByTagOperationProcessor implements FindItemByTagOperation {
    private final ZooStoreRestExport zooStoreRestExport;
    private final ItemStorageRestExport storageRestExport;

    @Override
    public FindAllItemsByTagResponse process(final FindAllItemsByTagRequest findItemByTagRequest) {
        FindItemsByTagResponse zooStoreItemsByTagId;

        zooStoreItemsByTagId = zooStoreRestExport.getItemByTagId(
                findItemByTagRequest.getPageNumber(),
                findItemByTagRequest.getNumberOfItemsPerPage(),
                String.valueOf(findItemByTagRequest.getTagId())
        );

        List<FindAllItemsByTagInRepo> list = zooStoreItemsByTagId.getItems().stream()
                .map(this::mapToZooStore)
                .collect(Collectors.toList());

        return FindAllItemsByTagResponse.builder()
                .items(list)
                .build();
    }

    private FindAllItemsByTagInRepo mapToZooStore(FindItemsByTagInRepo zooStoreItem) {
        FindItemByIdResponse storageItem = storageRestExport.findItemById(String.valueOf(zooStoreItem.getItemId()));

        return FindAllItemsByTagInRepo.builder()
                .itemId(zooStoreItem.getItemId())
                .storageItemId(String.valueOf(storageItem.getId()))
                .productName(zooStoreItem.getProductName())
                .description(zooStoreItem.getDescription())
                .vendorId(zooStoreItem.getVendorId())
                .multimediaIds(zooStoreItem.getMultimediaIds())
                .tagIds(zooStoreItem.getTagIds())
                .isArchived(zooStoreItem.getIsArchived())
                .price(storageItem.getPrice())
                .quantity(storageItem.getQuantity())
                .build();
    }
}
