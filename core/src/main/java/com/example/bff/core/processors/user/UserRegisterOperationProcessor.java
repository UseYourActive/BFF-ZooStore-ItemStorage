package com.example.bff.core.processors.user;

import com.example.bff.api.operations.auth.register.UserRegisterOperation;
import com.example.bff.api.operations.auth.register.UserRegisterRequest;
import com.example.bff.api.operations.auth.register.UserRegisterResponse;
import com.example.bff.core.exceptions.AlreadyExistingPhoneNumberException;
import com.example.bff.core.exceptions.UserAlreadyExistsException;
import com.example.bff.persistence.entities.Role;
import com.example.bff.persistence.entities.ShoppingCart;
import com.example.bff.persistence.entities.User;
import com.example.bff.persistence.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserRegisterOperationProcessor implements UserRegisterOperation {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserRegisterResponse process(UserRegisterRequest input) {
        this.userRepository
                .findByEmail(input.getEmail())
                .ifPresent(e -> {
                    throw new UserAlreadyExistsException();
                });

        this.userRepository
                .findByPhoneNumber(input.getPhoneNumber())
                .ifPresent(e -> {
                    throw new AlreadyExistingPhoneNumberException();
                });

        Role role = this.userRepository.count() == 0 ? Role.ADMIN : Role.USER;

        String email = input.getEmail();
        String hashedPassword = this.passwordEncoder.encode(input.getPassword());
        String firstName = input.getFirstName();
        String lastName = input.getLastName();
        String phoneNumber = input.getPhoneNumber();

        User user = User
                .builder()
                .email(email)
                .password(hashedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .shoppingCart(new ShoppingCart())
                .role(role)
                .build();

        this.userRepository.save(user);

        return UserRegisterResponse
                .builder()
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .build();
    }
}
