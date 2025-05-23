package com.luiz.backendchallenge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luiz.backendchallenge.service.validation.ClaimValidationFactory;
import com.luiz.backendchallenge.service.validation.ClaimValidationStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;

@Service
public class JwtValidationService {

    private static final Logger logger = LoggerFactory.getLogger(JwtValidationService.class);

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

            for (String claimName : REQUIRED_CLAIMS) {
                String value = claims.get(claimName).asString();
                ClaimValidationStrategy strategy = ClaimValidationFactory.getStrategy(claimName);
                if (strategy == null) {
                    logger.error("No validation strategy found for claim '{}'", claimName);
                    return false;
                }
                if (!strategy.validate(value)) {
                    logger.warn("Validation failed for claim '{}', value '{}'", claimName, value);
                    return false;
                }
            }

            logger.info("JWT is valid");
            return true;

        } catch (JWTDecodeException e) {
            logger.error("Failed to decode JWT: {}", e.getMessage());
            return false;
        }
    }
}