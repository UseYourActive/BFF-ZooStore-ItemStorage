package com.example.bff.core.processors.cartitem.additem;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.persistence.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddItemToCartOperationProcessor implements AddItemToCartOperation {
    private final CartItemRepository cartItemRepository;

    @Override
    public AddItemToCartResponse process(AddItemToCartRequest addItemToCartRequest) {
        return null;
    }
}
