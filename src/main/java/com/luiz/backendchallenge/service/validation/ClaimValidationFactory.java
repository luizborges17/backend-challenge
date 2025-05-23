package com.luiz.backendchallenge.service.validation;

import java.util.HashMap;
import java.util.Map;

public class ClaimValidationFactory {
    private static final Map<String, ClaimValidationStrategy> strategies = new HashMap<>();

    static {
        register(new NameClaimValidation());
        register(new RoleClaimValidation());
        register(new SeedClaimValidation());
    }

    public static void register(ClaimValidationStrategy strategy) {
        strategies.put(strategy.getClaimName(), strategy);
    }

    public static ClaimValidationStrategy getStrategy(String claimName) {
        return strategies.get(claimName);
    }
}