package com.example.bff.core.processors.endowmentfoundation;

import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationResponse;
import com.example.bff.persistence.entities.EndowmentFoundation;
import com.example.bff.persistence.repositories.EndowmentFoundationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterNewEndowmentFoundationOperationProcessor implements RegisterNewEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public RegisterNewEndowmentFoundationResponse process(final RegisterNewEndowmentFoundationRequest registerNewEndowmentFoundationRequest) {
        log.info("Starting register new endowment foundation operation");

        EndowmentFoundation endowmentFoundation = EndowmentFoundation.builder()
                .totalAmountOfMoney(BigDecimal.ZERO)
                .address(registerNewEndowmentFoundationRequest.getAddress())
                .name(registerNewEndowmentFoundationRequest.getName())
                .donationAccount(registerNewEndowmentFoundationRequest.getDonationAccount())
                .build();
        log.info("Creating new endowment foundation with name = {}", endowmentFoundation.getName());

        EndowmentFoundation savedEndowmentFoundation = endowmentFoundationRepository.save(endowmentFoundation);
        log.info("Endowment foundation registered successfully with id = {}", savedEndowmentFoundation.getId());

        RegisterNewEndowmentFoundationResponse response = RegisterNewEndowmentFoundationResponse.builder()
                .id(String.valueOf(savedEndowmentFoundation.getId()))
                .totalAmountOfMoney(savedEndowmentFoundation.getTotalAmountOfMoney())
                .name(savedEndowmentFoundation.getName())
                .address(savedEndowmentFoundation.getAddress())
                .donationAccount(savedEndowmentFoundation.getDonationAccount())
                .build();
        log.info("Register new endowment foundation operation completed");

        return response;
    }
}
