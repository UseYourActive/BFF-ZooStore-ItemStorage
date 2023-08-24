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

import static com.example.bff.core.config.EndowmentFoundationLoggerMessages.*;

@RequiredArgsConstructor
@Slf4j
@Service
public class DonateToEndowmentFoundationOperationProcessor implements DonateToEndowmentFoundationOperation {
    private final EndowmentFoundationRepository endowmentFoundationRepository;

    @Override
    public DonateToEndowmentFoundationResponse process(DonateToEndowmentFoundationRequest donateToEndowmentFoundationRequest) {
        log.info(STARTING_DONATE_TO_ENDOWMENT_FOUNDATION_OPERATION);

        EndowmentFoundation endowmentFoundation = endowmentFoundationRepository.findById(donateToEndowmentFoundationRequest.getFoundationId())
                .orElseThrow(EndowmentFoundationNotFoundException::new);
        log.info(FOUND_ENDOWMENT_FOUNDATION_FOR_DONATION_WITH_ID, endowmentFoundation.getId());

        BigDecimal amountToDonate = donateToEndowmentFoundationRequest.getAmountToDonate();

        endowmentFoundation.setTotalAmountOfMoney(endowmentFoundation.getTotalAmountOfMoney().add(amountToDonate));
        log.info(DONATING_AMOUNT_ADDED_TO_ENDOWMENT_FOUNDATION_NEW_TOTAL, endowmentFoundation.getTotalAmountOfMoney());

        EndowmentFoundation savedEndowmentFoundation = endowmentFoundationRepository.save(endowmentFoundation);
        log.info(ENDOWMENT_FOUNDATION_UPDATED_WITH_ID, savedEndowmentFoundation.getId());

        DonateToEndowmentFoundationResponse response = DonateToEndowmentFoundationResponse.builder()
                .id(savedEndowmentFoundation.getId())
                .address(savedEndowmentFoundation.getAddress())
                .donationAccount(savedEndowmentFoundation.getDonationAccount())
                .totalAmountOfMoney(savedEndowmentFoundation.getTotalAmountOfMoney())
                .name(savedEndowmentFoundation.getName())
                .build();
        log.info(DONATE_TO_ENDOWMENT_FOUNDATION_OPERATION_COMPLETED);

        return response;
    }
}
