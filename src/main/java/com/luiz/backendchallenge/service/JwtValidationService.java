package com.luiz.backendchallenge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luiz.backendchallenge.util.PrimeUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
public class JwtValidationService {

    private static final Set<String> ALLOWED_ROLES = Set.of("Admin", "Member", "External");
    private static final Set<String> REQUIRED_CLAIMS = Set.of("Name", "Role", "Seed");

    public boolean validateJwt(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();

            return claims.keySet().containsAll(REQUIRED_CLAIMS)
                    && claims.size() == REQUIRED_CLAIMS.size()
                    && isValidName(claims.get("Name").asString())
                    && isValidRole(claims.get("Role").asString())
                    && isValidSeed(claims.get("Seed").asString());

        } catch (JWTDecodeException e) {
            return false;
        }
    }

    private boolean isValidName(String name) {
        return Optional.ofNullable(name)
                .filter(n -> n.length() <= 256)
                .map(n -> n.replaceAll("\\s+", ""))
                .filter(n -> n.matches("^[A-Za-z]+$"))
                .isPresent();
    }

    private boolean isValidRole(String role) {
        return ALLOWED_ROLES.contains(role);
    }

    private boolean isValidSeed(String seedStr) {
        return Optional.ofNullable(seedStr)
                .flatMap(this::parseIntSafe)
                .filter(PrimeUtils::isPrime)
                .isPresent();
    }

    private Optional<Integer> parseIntSafe(String value) {
        try {
            return Optional.of(Integer.parseInt(value));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}
