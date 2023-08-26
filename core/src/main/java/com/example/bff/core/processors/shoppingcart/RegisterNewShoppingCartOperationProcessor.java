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

@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterNewShoppingCartOperationProcessor implements RegisterNewShoppingCartOperation {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    public RegisterNewShoppingCartResponse process(final RegisterNewShoppingCartRequest registerNewShoppingCartRequest) {
        log.info("Processing new shopping cart registration");

        User user = userRepository.findById(registerNewShoppingCartRequest.getUserId())
                .orElseThrow(UserNotFoundException::new);
        log.info("User has successfully been found in the database with id = {}", user.getId());

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(new ArrayList<>())
                .user(user)
                .build();
        log.info("Creating new shopping cart for user = {}", user.getId());

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info("Shopping cart registered successfully with id = {}", savedShoppingCart.getId());

        RegisterNewShoppingCartResponse build = RegisterNewShoppingCartResponse.builder()
                .cartId(savedShoppingCart.getId())
                .userId(savedShoppingCart.getUser().getId())
                .items(savedShoppingCart.getItems().stream()
                        .map(CartItem::getId)
                        .collect(Collectors.toList()))
                .quantity(savedShoppingCart.getItems().size())
                .build();
        log.info("New shopping cart registration process completed");

        return build;
    }
}
