package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

@RestController
public class HelloController {

    @GetMapping("/")
    public Map<String, Object> root() {
        return Map.of(
                "service", "spring-demo",
                "version", "0.1.0",
                "now", Instant.now().toString()
        );
    }

    @GetMapping("/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "world") String name) {
        return Map.of("message", "hello " + name);
    }
}