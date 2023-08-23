package com.example.bff.core.processors.shoppingcart;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import com.example.storage.restexport.StorageRestClient;
import com.example.zoostore.api.operations.item.find.byid.FindItemByIdResponse;
import com.example.zoostore.restexport.ZooStoreRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AddCartItemToShoppingCartOperationProcessor implements AddItemToCartOperation {
    private final CartItemRepository cartItemRepository;
    private final ZooStoreRestClient zooStoreRestClient;
    private final StorageRestClient storageRestClient;
    private final UserRepository userRepository;

    @Override
    public AddItemToCartResponse process(final AddItemToCartRequest addItemToCartRequest) {
        FindItemByIdResponse itemByIdZooStore;
        com.example.storage.api.operations.storageitem.find.byid.FindItemByIdResponse itemByIdStorage;
        User user = getAuthenticatedUser();

        try {
            itemByIdZooStore = zooStoreRestClient.getItemById(String.valueOf(addItemToCartRequest.getItemId()));
            itemByIdStorage = storageRestClient.findItemById(String.valueOf(addItemToCartRequest.getItemId()));
        }catch (Exception e){
            throw new ItemNotFoundException();
        }

//        CartItem itemForCart = CartItem.builder()
//                .targetItem(itemByIdStorage.getId())
//                .price(itemByIdStorage.getPrice())
//                .quantity(itemByIdStorage.getQuantity())
//                .build();
//
////        cartItemRepository.save(itemForCart);
////        user.getCartItems().add(itemForCart);
////        userRepository.save(user);
//
//        return AddItemToCartResponse.builder()
//                .price(itemForCart.getPrice())
//                .quantity(itemForCart.getQuantity())
//                .targetItemId(itemForCart.getTargetItem())
//                .build();
        return null;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}