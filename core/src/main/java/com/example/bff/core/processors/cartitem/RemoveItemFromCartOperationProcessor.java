package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRepo;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.bff.core.config.CartItemLoggerMessages.*;
import static com.example.bff.core.config.UserLoggerMessages.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class RemoveItemFromCartOperationProcessor implements RemoveItemFromCartOperation {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public RemoveItemFromCartResponse process(final RemoveItemFromCartRequest removeItemFromCartRequest) {
        log.info(STARTING_REMOVE_ITEM_FROM_CART_OPERATION);

        User user = getAuthenticatedUser();
        log.info(AUTHENTICATED_USER, user.getEmail());

        CartItem cartItem = cartItemRepository.findById(removeItemFromCartRequest.getTargetItemId())
                .orElseThrow(ItemNotFoundException::new);
        log.info(FOUND_CART_ITEM_TO_REMOVE_WITH_ID, cartItem.getId());

        this.cartItemRepository.delete(cartItem);
        log.info(CART_ITEM_REMOVED_SUCCESSFULLY);

        List<RemoveItemFromCartRepo> updatedCartItems = user.getShoppingCart().getItems().stream()
                .map(this::mapCartItem)
                .collect(Collectors.toList());
        log.info(MAPPED_REMAINING_CART_ITEMS_FOR_RESPONSE);

        RemoveItemFromCartResponse response = RemoveItemFromCartResponse.builder()
                .itemFromCartRepo(updatedCartItems)
                .build();
        log.info(REMOVE_ITEM_FROM_CART_OPERATION_COMPLETED);

        return response;
    }

    private RemoveItemFromCartRepo mapCartItem(CartItem cartItem){
        return RemoveItemFromCartRepo.builder()
                .id(cartItem.getId())
                .targetItemId(cartItem.getTargetItemId())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .reviews(cartItem.getReviews().stream()
                        .map(ItemReview::getId)
                        .collect(Collectors.toList()))
                .build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info(AUTHENTICATED_USER_WITH_EMAIL, email);

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error(USER_WITH_EMAIL_NOT_FOUND, email);
                    return new UsernameNotFoundException("The email you entered does not exist!");
                });
    }
}
