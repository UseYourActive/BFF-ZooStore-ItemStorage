package com.example.bff.rest.advices;

import com.example.bff.core.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.RestClientException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RestClientException.class)
    public ResponseEntity<String> handleRestClientException(RestClientException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error occurred during API call! " + e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("That was an invalid request input! " + e.getMessage());
    }

    @ExceptionHandler(AlreadyExistingPhoneNumberException.class)
    public ResponseEntity<String> handleAlreadyExistingPhoneNumberException(AlreadyExistingPhoneNumberException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Phone number already exists! " + e.getMessage());
    }

    @ExceptionHandler(CurrentPasswordInvalidException.class)
    public ResponseEntity<String> handleCurrentPasswordInvalidException(CurrentPasswordInvalidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid password! " + e.getMessage());
    }

    @ExceptionHandler(EndowmentFoundationNotFoundException.class)
    public ResponseEntity<String> handleEndowmentFoundationNotFoundException(EndowmentFoundationNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Endowment foundation not found! " + e.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid credentials! " + e.getMessage());
    }

    @ExceptionHandler(ItemReviewNotFoundException.class)
    public ResponseEntity<String> handleItemReviewNotFoundException(ItemReviewNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Item review not found! " + e.getMessage());
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<String> handleJWTVerificationException(JWTVerificationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JWT not verified! " + e.getMessage());
    }

    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleNoSuchUserException(NoSuchUserException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such user was found in the database! " + e.getMessage());
    }

    @ExceptionHandler(NotEnoughOfItemQuantityException.class)
    public ResponseEntity<String> handleNotEnoughOfItemQuantityException(NotEnoughOfItemQuantityException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not enough of item quantity! " + e.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<String> handleProductNotFoundException(ProductNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product was not found! " + e.getMessage());
    }

    @ExceptionHandler(ShoppingCartNotFoundException.class)
    public ResponseEntity<String> handleShoppingCartNotFoundException(ShoppingCartNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Shopping cart was not found! " + e.getMessage());
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> handleUserAlreadyExistsException(UserAlreadyExistsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exists! " + e.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User was not found! " + e.getMessage());
    }
}
