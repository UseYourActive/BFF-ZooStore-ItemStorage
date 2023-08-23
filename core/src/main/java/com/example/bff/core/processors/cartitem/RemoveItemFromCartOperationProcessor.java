package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRepo;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
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

        return RemoveItemFromCartResponse.builder()
                .itemFromCartRepo(user.getShoppingCart().getItems()
                        .stream()
                        .map(this::mapCartItem)
                        .collect(Collectors.toList()))
                .build();
    }

    private RemoveItemFromCartRepo mapCartItem(CartItem cartItem){
        return RemoveItemFromCartRepo.builder()
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

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
