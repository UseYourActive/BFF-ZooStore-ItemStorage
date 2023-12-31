package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartOperation;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartRepo;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartRequest;
import com.example.bff.api.operations.cartitem.remove.RemoveItemFromCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class RemoveItemFromCartOperationProcessor implements RemoveItemFromCartOperation {
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    @Override
    public RemoveItemFromCartResponse process(final RemoveItemFromCartRequest removeItemFromCartRequest) {
        log.info("Starting remove item from cart operation");

        User user = getAuthenticatedUser();
        log.info("Authenticated user = {}", user.getEmail());

        CartItem cartItem = cartItemRepository.findById(UUID.fromString(removeItemFromCartRequest.getTargetItemId()))
                .orElseThrow(ItemNotFoundException::new);
        log.info("Found cart item to remove with id = {}", cartItem.getId());

        this.cartItemRepository.delete(cartItem);
        log.info("Cart item removed successfully");

        List<RemoveItemFromCartRepo> updatedCartItems = user.getShoppingCart().getItems().stream()
                .map(this::mapCartItem)
                .collect(Collectors.toList());
        log.info("Mapped remaining cart items for response");

        RemoveItemFromCartResponse response = RemoveItemFromCartResponse.builder()
                .itemFromCartRepo(updatedCartItems)
                .build();
        log.info("Remove item from cart operation completed");

        return response;
    }

    private RemoveItemFromCartRepo mapCartItem(CartItem cartItem){
        List<UUID> collect = cartItem.getReviews().stream()
                .map(ItemReview::getId)
                .toList();

        return RemoveItemFromCartRepo.builder()
                .id(String.valueOf(cartItem.getId()))
                .targetItemId(String.valueOf(cartItem.getTargetItemId()))
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .reviews(collect.stream()
                        .map(UUID::toString)
                        .toList())
                .build();
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
