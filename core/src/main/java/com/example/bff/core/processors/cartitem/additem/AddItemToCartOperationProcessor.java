package com.example.bff.core.processors.cartitem.additem;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.core.exceptions.NotEnoughOfItemQuantityException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.api.operations.item.findbyid.FindItemByIdResponse;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddItemToCartOperationProcessor implements AddItemToCartOperation {
    private final CartItemRepository cartItemRepository;
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;
    private final UserRepository userRepository;

    @Override
    public AddItemToCartResponse process(final AddItemToCartRequest addItemToCartRequest) {
        com.example.storage.api.operations.findbyid.FindItemByIdResponse foundItemInStorage;
        FindItemByIdResponse foundItemInZooStore;
        User user = getAuthenticatedUser();

        try {
            foundItemInZooStore = zooStoreRestClient.getItemById(String.valueOf(addItemToCartRequest.getItemId()));
        }catch (Exception e){
            throw new RuntimeException("No such item found in ZooStore!");
        }

        try {
            foundItemInStorage = storageRestClient.getItemById(String.valueOf(addItemToCartRequest.getItemId()));
        }catch (Exception e){
            throw new RuntimeException("No such item found in ZooStorage!");
        }

        CartItem itemForCart = CartItem.builder()
                .targetItem(foundItemInStorage.getId())
                .price(foundItemInStorage.getPrice())
                .quantity(foundItemInStorage.getQuantity())
                .user(user)
                .build();

        cartItemRepository.save(itemForCart);
        user.getCartItems().add(itemForCart);
        userRepository.save(user);

        return AddItemToCartResponse.builder()
                .price(itemForCart.getPrice())
                .quantity(itemForCart.getQuantity())
                .targetItemId(itemForCart.getTargetItem())
                .userId(itemForCart.getUser().getId())
                .build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
