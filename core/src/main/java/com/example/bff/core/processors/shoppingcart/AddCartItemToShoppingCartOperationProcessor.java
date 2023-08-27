package com.example.bff.core.processors.shoppingcart;

import com.example.bff.api.operations.cartitem.add.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.add.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.add.AddItemToCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.CartItemRepository;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddCartItemToShoppingCartOperationProcessor implements AddItemToCartOperation {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    public AddItemToCartResponse process(final AddItemToCartRequest addItemToCartRequest) {
        log.info("Processing AddItemToCartOperation");

        User user = getAuthenticatedUser();
        log.info("Authenticated user: {}", user.getEmail());

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getShoppingCart().getId())
                .orElseThrow(ShoppingCartNotFoundException::new);
        log.info("Found shopping cart for user: {}", shoppingCart.getId());

        CartItem cartItem = cartItemRepository.findById(UUID.fromString(addItemToCartRequest.getItemId()))
                .orElseThrow(ItemNotFoundException::new);
        log.info("Found cart item: {}", cartItem.getId());

        shoppingCart.getItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        log.info("Added item to shopping cart");

        BigDecimal totalPriceOfCart = shoppingCart.getItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        AddItemToCartResponse response = AddItemToCartResponse.builder()
                .price(totalPriceOfCart)
                .quantity(shoppingCart.getItems().size())
                .targetItemId(String.valueOf(cartItem.getTargetItemId()))
                .build();
        log.info("AddItemToCartOperation completed");

        return response;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
