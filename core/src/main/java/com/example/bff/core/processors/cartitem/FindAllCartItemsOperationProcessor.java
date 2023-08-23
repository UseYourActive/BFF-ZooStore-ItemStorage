package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsInRepo;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRepo;
import com.example.bff.core.exceptions.ShoppingCartNotFoundException;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.ItemReview;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
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
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public FindAllCartItemsResponse process(final FindAllCartItemsRequest findAllCartItemsRequest) {
        User user = getAuthenticatedUser();

        ShoppingCart shoppingCart = this.shoppingCartRepository.findById(user.getId())
                .orElseThrow(ShoppingCartNotFoundException::new);

        return FindAllCartItemsResponse.builder()
                .items(shoppingCart.getItems().stream()
                        .map(this::mapCartItem)
                        .collect(Collectors.toList()))
                .build();
    }

    private FindAllCartItemsInRepo mapCartItem(CartItem cartItem){
        return FindAllCartItemsInRepo.builder()
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
