package com.example.bff.core.processors.auth;

import com.example.bff.api.operations.auth.register.UserRegisterOperation;
import com.example.bff.api.operations.auth.register.UserRegisterRequest;
import com.example.bff.api.operations.auth.register.UserRegisterResponse;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.ShoppingCartRepository;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegisterOperationProcessor implements UserRegisterOperation {
    private final UserRepository userRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterResponse process(UserRegisterRequest input) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCartRepository.save(shoppingCart);

        User user = User.builder()
                .email(input.getEmail())
                .password(passwordEncoder.encode(input.getPassword()))
                .firstName(input.getFirstName())
                .lastName(input.getLastName())
                .phoneNumber(input.getPhoneNumber())
                .shoppingCart(shoppingCart)
                .build();

        shoppingCart.setUser(user);
        this.shoppingCartRepository.save(shoppingCart);
        User persisted = this.userRepository.save(user);

        return UserRegisterResponse.builder()
                .id(persisted.getId())
                .email(persisted.getEmail())
                .firstName(persisted.getFirstName())
                .lastName(persisted.getLastName())
                .phoneNumber(persisted.getPhoneNumber())
                .build();
    }
}
