package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsInRepo;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
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
import static com.example.bff.core.config.ShoppingCartLoggerMessages.FOUND_SHOPPING_CART_FOR_USER;
import static com.example.bff.core.config.UserLoggerMessages.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public FindAllCartItemsResponse process(final FindAllCartItemsRequest findAllCartItemsRequest) {
        log.info(STARTING_FIND_ALL_CART_ITEMS_OPERATION);

        User user = getAuthenticatedUser();
        log.info(AUTHENTICATED_USER, user.getEmail());

        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(user.getId())
                .orElseThrow(ShoppingCartNotFoundException::new);
        log.info(FOUND_SHOPPING_CART_FOR_USER, user.getId());

        List<FindAllCartItemsInRepo> cartItems = shoppingCart.getItems().stream()
                .map(this::mapCartItem)
                .collect(Collectors.toList());
        log.info(MAPPED_CART_ITEMS_FOR_RESPONSE);

        FindAllCartItemsResponse response = FindAllCartItemsResponse.builder()
                .items(cartItems)
                .build();
        log.info(FIND_ALL_CART_ITEMS_OPERATION_COMPLETED);

        return response;
    }

    private FindAllCartItemsInRepo mapCartItem(CartItem cartItem){
        return FindAllCartItemsInRepo.builder()
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
