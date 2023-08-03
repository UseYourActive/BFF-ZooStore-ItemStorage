package com.example.bff.core.processors.cartitem.removeitem;

import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RemoveItemFromCartOperationProcessor implements RemoveItemFromCartOperation {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public RemoveItemFromCartResponse process(final RemoveItemFromCartRequest removeItemFromCartRequest) {
        return null;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
