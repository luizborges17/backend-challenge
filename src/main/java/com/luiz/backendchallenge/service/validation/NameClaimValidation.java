package com.luiz.backendchallenge.service.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class NameClaimValidation implements ClaimValidationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(NameClaimValidation.class);

    @Override
    public boolean validate(String name) {
        boolean result = Optional.ofNullable(name)
                .filter(n -> n.length() <= 256)
                .map(n -> n.replaceAll("\\s+", ""))
                .filter(n -> n.matches("^[A-Za-z]+$"))
                .isPresent();
        if (!result) {
            logger.debug("Name validation failed for '{}'", name);
        }
        return result;
    }

    @Override
    public String getClaimName() {
        return "Name";
    }
}
