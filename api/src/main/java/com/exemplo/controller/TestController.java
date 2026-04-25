package com.exemplo.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Value("${app.name:Meu Projeto Javaxx}")
    private String appName;

    @GetMapping("/health")
    public Map<String, String> healthCheck() {
        return Map.of(
                "status", "UP",
                "message", "API REST rodando no Docker com sucesso!",
                "app_name", appName);
    }
}
