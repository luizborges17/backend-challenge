package com.luiz.backendchallenge.service;


import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luiz.backendchallenge.util.PrimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class JwtValidationService {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationService.class);

    private static final Set<String> ALLOWED_ROLES = Set.of("Admin", "Member", "External");
    private static final Set<String> REQUIRED_CLAIMS = Set.of("Name", "Role", "Seed");

    public boolean validateJwt(String token) {
        try {
            logger.info("Starting JWT validation");
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            logger.debug("Decoded JWT claims: {}", claims.keySet());

            if (!claims.keySet().containsAll(REQUIRED_CLAIMS)) {
                logger.warn("JWT is missing required claims. Required: {}, Present: {}", REQUIRED_CLAIMS, claims.keySet());
                return false;
            }

            if (claims.size() != REQUIRED_CLAIMS.size()) {
                logger.warn("JWT has extra or missing claims. Expected count: {}, Actual count: {}", REQUIRED_CLAIMS.size(), claims.size());
                return false;
            }

            String name = claims.get("Name").asString();
            String role = claims.get("Role").asString();
            String seed = claims.get("Seed").asString();
            logger.debug("Claim values - Name: {}, Role: {}, Seed: {}", name, role, seed);

            if (!isValidName(name)) {
                logger.warn("Invalid Name claim: '{}'", name);
                return false;
            }

            if (!isValidRole(role)) {
                logger.warn("Invalid Role claim: '{}'", role);
                return false;
            }

            if (!isValidSeed(seed)) {
                logger.warn("Invalid Seed claim: '{}'", seed);
                return false;
            }

            logger.info("JWT is valid");
            return true;

        } catch (JWTDecodeException e) {
            logger.error("Failed to decode JWT: {}", e.getMessage());
            return false;
        }
    }

    private boolean isValidName(String name) {
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

    private boolean isValidRole(String role) {
        boolean result = ALLOWED_ROLES.contains(role);
        if (!result) {
            logger.debug("Role validation failed for '{}'", role);
        }
        return result;
    }

    private boolean isValidSeed(String seedStr) {
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
}