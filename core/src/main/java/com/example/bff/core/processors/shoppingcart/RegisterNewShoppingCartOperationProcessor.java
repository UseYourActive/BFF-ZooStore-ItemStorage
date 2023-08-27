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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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

        User user = getAuthenticatedUser();
        log.info("User has successfully been found in the database with id = {}", user.getId());

        ShoppingCart shoppingCart = ShoppingCart.builder()
                .items(new ArrayList<>())
                .user(user)
                .build();
        log.info("Creating new shopping cart for user = {}", user.getId());

        ShoppingCart savedShoppingCart = shoppingCartRepository.save(shoppingCart);
        log.info("Shopping cart registered successfully with id = {}", savedShoppingCart.getId());

        user.setShoppingCart(savedShoppingCart);
        userRepository.save(user);
        log.info("User successfully saved shopping cart!");

        List<UUID> collect = savedShoppingCart.getItems().stream()
                .map(CartItem::getId)
                .toList();

        RegisterNewShoppingCartResponse build = RegisterNewShoppingCartResponse.builder()
                .cartId(String.valueOf(savedShoppingCart.getId()))
                .userId(String.valueOf(savedShoppingCart.getUser().getId()))
                .items(collect.stream()
                        .map(UUID::toString)
                        .toList())
                .quantity(savedShoppingCart.getItems().size())
                .build();
        log.info("New shopping cart registration process completed");

        return build;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        log.info("Authenticated user with email = {}", email);

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error("User with email '{}' not found", email);
                    return new UsernameNotFoundException("The email you entered does not exist!");
                });
    }
}
