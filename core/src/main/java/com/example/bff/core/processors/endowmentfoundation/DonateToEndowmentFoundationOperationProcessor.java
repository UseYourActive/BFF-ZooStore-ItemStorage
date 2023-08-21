package com.example.bff.core.processors.endowmentfoundation;

import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationResponse;
import com.example.bff.core.exceptions.EndowmentFoundationNotFoundException;
import com.example.bff.persistence.entities.EndowmentFoundation;
import com.example.bff.persistence.repositories.EndowmentFoundationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DonateToEndowmentFoundationOperationProcessor implements DonateToEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public DonateToEndowmentFoundationResponse process(DonateToEndowmentFoundationRequest donateToEndowmentFoundationRequest) {
        EndowmentFoundation endowmentFoundation = endowmentFoundationRepository.findById(donateToEndowmentFoundationRequest.getFoundationId())
                .orElseThrow(EndowmentFoundationNotFoundException::new);

        endowmentFoundation.setTotalAmountOfMoney(endowmentFoundation.getTotalAmountOfMoney().add(donateToEndowmentFoundationRequest.getAmountToDonate()));

        EndowmentFoundation save = endowmentFoundationRepository.save(endowmentFoundation);

        return DonateToEndowmentFoundationResponse.builder()
                .id(save.getId())
                .address(save.getAddress())
                .donationAccount(save.getDonationAccount())
                .totalAmountOfMoney(save.getTotalAmountOfMoney())
                .name(save.getName())
                .build();
    }
}
