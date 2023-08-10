package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsInRepo;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsOperation;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsRequest;
import com.example.bff.api.operations.cartitem.findall.FindAllCartItemsResponse;
import com.example.bff.api.operations.cartitem.removeitem.RemoveItemFromCartRepo;
import com.example.bff.persistence.entities.CartItem;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class FindAllCartItemsOperationProcessor implements FindAllCartItemsOperation {
    private final UserRepository userRepository;

    @Override
    public FindAllCartItemsResponse process(final FindAllCartItemsRequest findAllCartItemsRequest) {
        //User user = getAuthenticatedUser();

        return null;
//        return new FindAllCartItemsResponse(
//                user.getCartItems().stream()
//                        .map(this::mapCartItem)
//                        .collect(Collectors.toSet())
//        );
    }

    private FindAllCartItemsInRepo mapCartItem(CartItem cartItem){
        return FindAllCartItemsInRepo.builder()
                .targetItemId(cartItem.getTargetItem())
                .price(cartItem.getPrice())
                .quantity(cartItem.getQuantity())
                .userId(cartItem.getUser().getId())
                .build();
    }

//    private User getAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        return userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
//    }
}
