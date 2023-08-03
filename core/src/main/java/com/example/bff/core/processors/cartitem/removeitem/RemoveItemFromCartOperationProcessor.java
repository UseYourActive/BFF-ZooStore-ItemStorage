package com.example.bff.core.processors.cartitem.removeitem;

import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRepo;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RemoveItemFromCartOperationProcessor implements RemoveItemFromCartOperation {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public RemoveItemFromCartResponse process(final RemoveItemFromCartRequest removeItemFromCartRequest) {
        User user = getAuthenticatedUser();

        CartItem cartItem = cartItemRepository.findById(removeItemFromCartRequest.getTargetItemId())
                .orElseThrow(ItemNotFoundException::new);

        this.cartItemRepository.delete(cartItem);

        return new RemoveItemFromCartResponse(
                user.getCartItems().stream()
                        .map(this::mapCartItem)
                        .collect(Collectors.toSet())
        );
    }

    private RemoveItemFromCartRepo mapCartItem(CartItem cartItem){
        return RemoveItemFromCartRepo.builder()
                .userId(cartItem.getUser().getId())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .targetItemId(cartItem.getTargetItem())
                .build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
