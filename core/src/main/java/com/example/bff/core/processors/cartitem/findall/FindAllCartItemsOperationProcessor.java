package com.example.bff.core.processors.cartitem.findall;

import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
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
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public FindAllCartItemsResponse process(final FindAllCartItemsRequest findAllCartItemsRequest) {
        return null;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
