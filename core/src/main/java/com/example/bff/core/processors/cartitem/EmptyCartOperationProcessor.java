package com.example.bff.core.processors.cartitem;

import com.example.bff.api.operations.cartitem.empty.EmptyCartOperation;
import com.example.bff.api.operations.cartitem.empty.EmptyCartRequest;
import com.example.bff.api.operations.cartitem.empty.EmptyCartResponse;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmptyCartOperationProcessor implements EmptyCartOperation {
    private final UserRepository userRepository;

    @Override
    public EmptyCartResponse process(EmptyCartRequest emptyCartRequest) {
        //User user = getAuthenticatedUser();

//        user.getCartItems().forEach(user::removeItemFromCart);
//        //user.getCartItems().clear();
//        userRepository.save(user);

        return new EmptyCartResponse();
    }

//    private User getAuthenticatedUser() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        String email = authentication.getName();
//
//        return userRepository.findUserByEmail(email)
//                .orElseThrow(() -> new UsernameNotFoundException("The email you entered does not exist!"));
//    }
}
