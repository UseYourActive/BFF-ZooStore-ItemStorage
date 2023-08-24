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

import static com.example.bff.core.config.EndowmentFoundationLoggerMessages.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class RegisterNewEndowmentFoundationOperationProcessor implements RegisterNewEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public RegisterNewEndowmentFoundationResponse process(RegisterNewEndowmentFoundationRequest registerNewEndowmentFoundationRequest) {
        log.info(STARTING_REGISTER_NEW_ENDOWMENT_FOUNDATION_OPERATION);

        EndowmentFoundation endowmentFoundation = EndowmentFoundation.builder()
                .totalAmountOfMoney(BigDecimal.ZERO)
                .address(registerNewEndowmentFoundationRequest.getAddress())
                .name(registerNewEndowmentFoundationRequest.getName())
                .donationAccount(registerNewEndowmentFoundationRequest.getDonationAccount())
                .build();
        log.info(CREATING_NEW_ENDOWMENT_FOUNDATION_WITH_NAME, endowmentFoundation.getName());

        EndowmentFoundation savedEndowmentFoundation = endowmentFoundationRepository.save(endowmentFoundation);
        log.info(ENDOWMENT_FOUNDATION_REGISTERED_SUCCESSFULLY_WITH_ID, savedEndowmentFoundation.getId());

        RegisterNewEndowmentFoundationResponse response = RegisterNewEndowmentFoundationResponse.builder()
                .id(savedEndowmentFoundation.getId())
                .totalAmountOfMoney(savedEndowmentFoundation.getTotalAmountOfMoney())
                .name(savedEndowmentFoundation.getName())
                .address(savedEndowmentFoundation.getAddress())
                .donationAccount(savedEndowmentFoundation.getDonationAccount())
                .build();
        log.info(REGISTER_NEW_ENDOWMENT_FOUNDATION_OPERATION_COMPLETED);

        return response;
    }
}
