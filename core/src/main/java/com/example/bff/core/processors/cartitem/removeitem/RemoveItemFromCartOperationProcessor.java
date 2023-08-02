package com.example.bff.core.processors.cartitem.removeitem;

import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.persistence.repositories.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RemoveItemFromCartOperationProcessor implements RemoveItemFromCartOperation {
    private final CartItemRepository cartItemRepository;

    @Override
    public RemoveItemFromCartResponse process(RemoveItemFromCartRequest removeItemFromCartRequest) {
        return null;
    }
}
