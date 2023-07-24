package com.example.bff.api.base;

public interface OperationProcessor<Response extends OperationResult, Request extends OperationInput> {
    Response process(Request request);
}
