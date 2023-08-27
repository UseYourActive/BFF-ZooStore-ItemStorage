package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsInRepo;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.find.findall.FindAllCartItemsResponse;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public FindAllCartItemsResponse process(final FindAllCartItemsRequest findAllCartItemsRequest) {
        log.info("Starting find all cart items operation");

        User user = getAuthenticatedUser();
        log.info("Authenticated user = {}", user.getEmail());

        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(user.getId())
                .orElseThrow(ShoppingCartNotFoundException::new);
        log.info("Found shopping cart for user = {}", user.getId());

        List<FindAllCartItemsInRepo> cartItems = shoppingCart.getItems().stream()
                .map(this::mapCartItem)
                .collect(Collectors.toList());
        log.info("Mapped cart items for response");

        FindAllCartItemsResponse response = FindAllCartItemsResponse.builder()
                .items(cartItems)
                .build();
        log.info("Find all cart items operation completed");

        return response;
    }

    private FindAllCartItemsInRepo mapCartItem(CartItem cartItem){
        List<UUID> collect = cartItem.getReviews().stream()
                .map(ItemReview::getId)
                .toList();

        return FindAllCartItemsInRepo.builder()
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
