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

import static com.example.bff.core.config.ShoppingCartLoggerMessages.*;
import static com.example.bff.core.config.UserLoggerMessages.AUTHENTICATED_USER;
import static com.example.bff.core.config.UserLoggerMessages.USER_WITH_EMAIL_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class EmptyCartOperationProcessor implements EmptyCartOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public EmptyCartResponse process(EmptyCartRequest emptyCartRequest) {
        log.info(STARTING_EMPTY_CART_OPERATION);

        User user = getAuthenticatedUser();
        log.info(AUTHENTICATED_USER, user.getEmail());

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getShoppingCart().getId())
                .orElseThrow(ShoppingCartNotFoundException::new);
        log.info(FOUND_SHOPPING_CART_FOR_USER, user.getId());

        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);

        log.info(SHOPPING_CART_EMPTIED_SUCCESSFULLY);

        return new EmptyCartResponse();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> {
                    log.error(USER_WITH_EMAIL_NOT_FOUND, email);
                    return new UsernameNotFoundException("The email you entered does not exist!");
                });
    }
}
