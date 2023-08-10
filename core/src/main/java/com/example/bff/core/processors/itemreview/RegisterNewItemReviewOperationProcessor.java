package com.example.bff.core.processors.itemreview;

import com.example.bff.api.operations.itemreview.RegisterNewItemReviewOperation;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewRequest;
import com.example.bff.api.operations.itemreview.RegisterNewItemReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RegisterNewItemReviewOperationProcessor implements RegisterNewItemReviewOperation {
    @Override
    public RegisterNewItemReviewResponse process(RegisterNewItemReviewRequest registerNewItemReviewRequest) {
        return null;
    }
}
