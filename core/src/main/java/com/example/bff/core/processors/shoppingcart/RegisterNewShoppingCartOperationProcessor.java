package com.example.bff.core.processors.shoppingcart;

import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartOperation;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartRequest;
import com.example.bff.api.operations.shoppingcart.register.RegisterNewShoppingCartResponse;
import com.example.bff.core.exceptions.UserNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.example.bff.core.config.ShoppingCartLoggerMessages.*;
import static com.example.bff.core.config.UserLoggerMessages.USER_FOUND_IN_DATABASE_WITH_ID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterNewShoppingCartOperationProcessor implements RegisterNewShoppingCartOperation {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    public RegisterNewShoppingCartResponse process(RegisterNewShoppingCartRequest registerNewShoppingCartRequest) {
        log.info(PROCESSING_NEW_SHOPPING_CART_REGISTRATION);

        User user = userRepository.findById(registerNewShoppingCartRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);
        log.info(USER_FOUND_IN_DATABASE_WITH_ID, user.getId());

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(new ArrayList<>())
                .user(user)
                .build();
        log.info(CREATING_NEW_SHOPPING_CART_FOR_USER_WITH_ID, user.getId());

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info(SHOPPING_CART_REGISTERED_SUCCESSFULLY_WITH_ID, savedShoppingCart.getId());

        RegisterNewShoppingCartResponse build = RegisterNewShoppingCartResponse.builder()
                .cartId(savedShoppingCart.getId())
                .userId(savedShoppingCart.getUser().getId())
                .items(savedShoppingCart.getItems().stream()
                        .map(CartItem::getId)
                        .collect(Collectors.toList()))
                .quantity(savedShoppingCart.getItems().size())
                .build();
        log.info(NEW_SHOPPING_CART_REGISTRATION_PROCESS_COMPLETED);

        return build;
    }
}
