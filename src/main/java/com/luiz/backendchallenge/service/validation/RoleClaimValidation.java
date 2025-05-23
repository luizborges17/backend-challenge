package com.luiz.backendchallenge.service.validation;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleClaimValidation implements ClaimValidationStrategy {
    private static final Logger logger = LoggerFactory.getLogger(RoleClaimValidation.class);
    private static final Set<String> ALLOWED_ROLES = Set.of("Admin", "Member", "External");

    @Override
    public boolean validate(String role) {
        boolean result = ALLOWED_ROLES.contains(role);
        if (!result) {
            logger.debug("Role validation failed for '{}'", role);
        }
        return result;
    }

    @Override
    public String getClaimName() {
        return "Role";
    }
}
