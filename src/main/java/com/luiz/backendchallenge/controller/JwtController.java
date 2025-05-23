package com.luiz.backendchallenge.controller;


import com.luiz.backendchallenge.model.JwtValidationRequest;
import com.luiz.backendchallenge.model.JwtValidationResponse;
import com.luiz.backendchallenge.service.JwtValidationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class JwtController {

    public JwtController(JwtValidationService validationService) {
        this.validationService = validationService;
    }

    private final JwtValidationService validationService;

    @PostMapping("/validate")
    public JwtValidationResponse validateJwt(@RequestBody JwtValidationRequest request) {
        boolean isValid = validationService.validateJwt(request.jwt());
        return new JwtValidationResponse(isValid);
    }
}
