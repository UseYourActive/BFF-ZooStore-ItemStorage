package com.example.bff.core.processors.shoppingcart;

import com.example.bff.api.operations.cartitem.additem.AddItemToCartOperation;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartRequest;
import com.example.bff.api.operations.cartitem.additem.AddItemToCartResponse;
import com.example.bff.core.exceptions.ItemNotFoundException;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
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

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class AddCartItemToShoppingCartOperationProcessor implements AddItemToCartOperation {
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;

    @Override
    public AddItemToCartResponse process(final AddItemToCartRequest addItemToCartRequest) {
        User user = getAuthenticatedUser();

        ShoppingCart shoppingCart = shoppingCartRepository.findById(user.getShoppingCart().getId())
                .orElseThrow(ShoppingCartNotFoundException::new);

        CartItem cartItem = cartItemRepository.findById(addItemToCartRequest.getItemId())
                .orElseThrow(ItemNotFoundException::new);

        shoppingCart.getItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);

        BigDecimal totalPriceOfCart = shoppingCart.getItems().stream()
                .map(CartItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return AddItemToCartResponse.builder()
                .price(totalPriceOfCart)
                .quantity(shoppingCart.getItems().size())
                .targetItemId(cartItem.getTargetItemId())
                .build();
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        return userRepository.findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
    }
}
