package com.example.bff.core.processors.cartitem.findall;

import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.persistence.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final CartItemRepository cartItemRepository;

    @Override
    public FindAllCartItemsResponse process(FindAllCartItemsRequest findAllCartItemsRequest) {
        return null;
    }
}
