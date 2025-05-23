package com.luiz.backendchallenge.service.validation;

public interface ClaimValidationStrategy {
    boolean validate(String value);
    String getClaimName();
}
