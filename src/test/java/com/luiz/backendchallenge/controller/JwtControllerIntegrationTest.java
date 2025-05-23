package com.luiz.backendchallenge.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luiz.backendchallenge.model.JwtValidationRequest;
import com.luiz.backendchallenge.model.JwtValidationResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class JwtControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SECRET = "secret";

    @Test
    void shouldReturnTrueForValidJwt() throws Exception {
        String jwt = JWT.create()
                .withClaim("Role", "Admin")
                .withClaim("Seed", "7841")
                .withClaim("Name", "Toninho Araujo")
                .sign(Algorithm.HMAC256(SECRET));

        JwtValidationRequest request = new JwtValidationRequest(jwt);

        String response = mockMvc.perform(post("/api/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JwtValidationResponse respObj = objectMapper.readValue(response, JwtValidationResponse.class);
        assertThat(respObj.isValid()).isTrue();
    }

    @Test
    void shouldReturnFalseForInvalidJwt() throws Exception {
        JwtValidationRequest request = new JwtValidationRequest("invalid.jwt.token");

        String response = mockMvc.perform(post("/api/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JwtValidationResponse respObj = objectMapper.readValue(response, JwtValidationResponse.class);
        assertThat(respObj.isValid()).isFalse();
    }
}