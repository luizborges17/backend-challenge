package com.luiz.backendchallenge.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JwtValidationServiceTest {

    private JwtValidationService service;
    private final String SECRET = "secret";

    @BeforeEach
    void setUp() {
        service = new JwtValidationService();
    }

    @Test
    void testValidJwt() {
        String token = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Seed", "7841")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));
        assertTrue(service.validateJwt(token));
    }

    @Test
    void testInvalidJwtMalformed() {
        String token = "eyJhbGciOiJzI1NiJ9.dfsdfsfryJSr2xrIjoiQWRtaW4iLCJTZrkIjoiNzg0MSIsIk5hbrUiOiJUb25pbmhvIEFyYXVqbyJ9.QY05fsdfsIjtrcJnP533kQNk8QXcaleJ1Q01jWY_ZzIZuAg";
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testNameWithNumber() {
        String token = JWT.create()
                .withClaim("Role", "External")
                .withClaim("Seed", "72341")
                .withClaim("Name", "M4ria Olivia")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testExtraClaims() {
        String token = JWT.create()
                .withClaim("Role", "Member")
                .withClaim("Seed", "14627")
                .withClaim("Name", "Valdir Aranha")
                .withClaim("Org", "BR")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testNameTooLong() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 257; i++) sb.append("A");
        String token = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Seed", "7841")
                .withClaim("Name", sb.toString())
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testRoleInvalid() {
        String token = JWT.create()
                .withClaim("Role", "SuperAdmin")
                .withClaim("Seed", "7841")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testSeedNotPrime() {
        String token = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Seed", "10")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testSeedNotNumber() {
        String token = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Seed", "abc")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }

    @Test
    void testMissingClaims() {
        String token = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));
        assertFalse(service.validateJwt(token));
    }
}
