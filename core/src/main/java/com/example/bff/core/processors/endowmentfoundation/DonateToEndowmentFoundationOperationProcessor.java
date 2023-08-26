package com.example.bff.core.processors.endowmentfoundation;

import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationOperation;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationRequest;
import com.example.bff.api.operations.endowmentfoundation.donate.DonateToEndowmentFoundationResponse;
import com.example.bff.core.exceptions.EndowmentFoundationNotFoundException;
import com.example.bff.persistence.entities.EndowmentFoundation;
import com.example.bff.persistence.repositories.EndowmentFoundationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Slf4j
@Service
public class DonateToEndowmentFoundationOperationProcessor implements DonateToEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public DonateToEndowmentFoundationResponse process(final DonateToEndowmentFoundationRequest donateToEndowmentFoundationRequest) {
        log.info("Starting donate to endowment foundation operation");

        EndowmentFoundation endowmentFoundation = endowmentFoundationRepository.findById(donateToEndowmentFoundationRequest.getFoundationId())
                .orElseThrow(EndowmentFoundationNotFoundException::new);
        log.info("Found endowment foundation for donation with id = {}", endowmentFoundation.getId());

        BigDecimal amountToDonate = donateToEndowmentFoundationRequest.getAmountToDonate();

        endowmentFoundation.setTotalAmountOfMoney(endowmentFoundation.getTotalAmountOfMoney().add(amountToDonate));
        log.info("Donation amount added to endowment foundation new total = {}", endowmentFoundation.getTotalAmountOfMoney());

        EndowmentFoundation savedEndowmentFoundation = endowmentFoundationRepository.save(endowmentFoundation);
        log.info("Endowment foundation updated with id = {}", savedEndowmentFoundation.getId());

        DonateToEndowmentFoundationResponse response = DonateToEndowmentFoundationResponse.builder()
                .id(savedEndowmentFoundation.getId())
                .address(savedEndowmentFoundation.getAddress())
                .donationAccount(savedEndowmentFoundation.getDonationAccount())
                .totalAmountOfMoney(savedEndowmentFoundation.getTotalAmountOfMoney())
                .name(savedEndowmentFoundation.getName())
                .build();
        log.info("Donate to endowment foundation operation completed");

        return response;
    }
}
