package com.example.itau.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Check")
@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @Operation(summary = "funcionamento do sistema", description = "verifica se o sistema está funcionando")
    @GetMapping
    public String healthCheck() {
        return "Aplicação está funcionando corretamente!";
    }
}