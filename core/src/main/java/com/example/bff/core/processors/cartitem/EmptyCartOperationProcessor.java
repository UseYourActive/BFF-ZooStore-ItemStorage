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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmptyCartOperationProcessor implements EmptyCartOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public EmptyCartResponse process(EmptyCartRequest emptyCartRequest) {
        User user = getAuthenticatedUser();

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getShoppingCart().getId())
                .orElseThrow(ShoppingCartNotFoundException::new);

        shoppingCart.getItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return new EmptyCartResponse();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
