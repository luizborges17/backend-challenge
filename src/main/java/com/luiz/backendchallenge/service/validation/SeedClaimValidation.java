package com.luiz.backendchallenge.service.validation;

import com.luiz.backendchallenge.util.PrimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class SeedClaimValidation implements ClaimValidationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(SeedClaimValidation.class);

    @Override
    public boolean validate(String seedStr) {
        boolean result = Optional.ofNullable(seedStr)
                .flatMap(this::parseIntSafe)
                .filter(PrimeUtils::isPrime)
                .isPresent();
        if (!result) {
            logger.debug("Seed validation failed for '{}'", seedStr);
        }
        return result;
    }

    private Optional<Integer> parseIntSafe(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            logger.debug("Failed to parse integer from '{}'", value);
            return Optional.empty();
        }
    }

    @Override
    public String getClaimName() {
        return "Seed";
    }
}