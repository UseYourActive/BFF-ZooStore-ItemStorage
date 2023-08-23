package com.example.bff.core.processors.endowmentfoundation;

import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.register.RegisterNewEndowmentFoundationResponse;
import com.example.bff.persistence.entities.EndowmentFoundation;
import com.example.bff.persistence.repositories.EndowmentFoundationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class RegisterNewEndowmentFoundationOperationProcessor implements RegisterNewEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public RegisterNewEndowmentFoundationResponse process(RegisterNewEndowmentFoundationRequest registerNewEndowmentFoundationRequest) {
        EndowmentFoundation endowmentFoundation = EndowmentFoundation.builder()
                .totalAmountOfMoney(BigDecimal.ZERO)
                .address(registerNewEndowmentFoundationRequest.getAddress())
                .name(registerNewEndowmentFoundationRequest.getName())
                .donationAccount(registerNewEndowmentFoundationRequest.getDonationAccount())
                .build();

        EndowmentFoundation save = endowmentFoundationRepository.save(endowmentFoundation);

        return RegisterNewEndowmentFoundationResponse.builder()
                .id(save.getId())
                .totalAmountOfMoney(save.getTotalAmountOfMoney())
                .name(save.getName())
                .address(save.getAddress())
                .donationAccount(save.getDonationAccount())
                .build();
    }
}
