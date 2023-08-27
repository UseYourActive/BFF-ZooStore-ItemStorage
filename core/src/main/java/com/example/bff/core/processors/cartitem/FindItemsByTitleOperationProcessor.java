package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.find.bytitle.FindItemsByTitleOperation;
import com.example.bff.api.operations.cartitem.find.bytitle.FindItemsByTitleRequest;
import com.example.bff.api.operations.cartitem.find.bytitle.FindItemsByTitleResponse;
import com.example.bff.api.operations.cartitem.find.bytitle.ItemsFoundAmountAndPriceData;
import com.example.storage.api.operations.storageitem.find.byid.FindItemByIdResponse;
import com.example.storage.restexport.ItemStorageRestExport;
import com.example.zoostore.api.operations.item.find.byproductname.FindItemsByProductNameResponse;
import com.example.zoostore.api.operations.item.find.byproductname.FindItemsByProductNamesRepo;
import com.example.zoostore.restexport.ZooStoreRestExport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class FindItemsByTitleOperationProcessor implements FindItemsByTitleOperation {
    private final ZooStoreRestExport zooStoreRestExport;
    private final ItemStorageRestExport itemStorageRestExport;

    @Override
    public FindItemsByTitleResponse process(FindItemsByTitleRequest findItemsByTitleRequest) {
        FindItemsByProductNameResponse itemsByProductName = this.zooStoreRestExport.findItemsByProductName(findItemsByTitleRequest.getTitle(), findItemsByTitleRequest.getPageNumber(), findItemsByTitleRequest.getPageSize());

        List<String> itemsFoundIds = itemsByProductName.getItems().stream()
                .map(FindItemsByProductNamesRepo::getItemId)
                .toList();

        List<FindItemByIdResponse> storageItems = itemsFoundIds.parallelStream()
                .map(itemStorageRestExport::findItemById)
                .toList();

        List<ItemsFoundAmountAndPriceData> items = new ArrayList<>();

        for (FindItemsByProductNamesRepo itemFromZooStore : itemsByProductName.getItems()) {
            storageItems.stream()
                    .filter(itemFromStorage -> itemFromStorage.getReferencedItemId().equals(itemFromZooStore.getItemId()))
                    .map(itemFromStorage -> mapToItemFromStorageAndZooStoreData(itemFromZooStore, itemFromStorage))
                    .forEach(items::add);
        }

        return FindItemsByTitleResponse.builder()
                .itemCount(Long.valueOf(items.size()))
                .items(items)
                .build();
    }

    private ItemsFoundAmountAndPriceData mapToItemFromStorageAndZooStoreData(FindItemsByProductNamesRepo zooStoreItem, FindItemByIdResponse storageItem) {
        return ItemsFoundAmountAndPriceData
                .builder()
                .id(zooStoreItem.getItemId())
                .title(zooStoreItem.getProductName())
                .description(zooStoreItem.getDescription())
                .vendorId(zooStoreItem.getVendorId())
                .multimedia(zooStoreItem.getMultimediaIds())
                .tags(zooStoreItem.getTagIds())
                .price(storageItem.getPrice())
                .quantity(storageItem.getQuantity())
                .build();
    }
}
