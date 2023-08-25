package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.empty.EmptyCartOperation;
import com.example.bff.api.operations.cartitem.empty.EmptyCartRequest;
import com.example.bff.api.operations.cartitem.empty.EmptyCartResponse;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmptyCartOperationProcessor implements EmptyCartOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public EmptyCartResponse process(EmptyCartRequest emptyCartRequest) {
        log.info("Starting empty cart operation");

        User user = getAuthenticatedUser();
        log.info("Authenticated user = {}", user.getEmail());

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getShoppingCart().getId())
                .orElseThrow(ShoppingCartNotFoundException::new);
        log.info("Found shopping cart for user = {}", user.getId());

        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);

        log.info("Shopping cart emptied successfully");

        return new EmptyCartResponse();
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
