package com.amazingcode.in.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class SpringBootLabApplication {
    
    private static final Logger LOG = LoggerFactory.getLogger(SpringBootLabApplication.class);

    @GetMapping
    public ResponseEntity<String> getApplicationStatus() {
        LOG.info("Request received for application status.");
        return ResponseEntity.ok("Application started successfully.");
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringBootLabApplication.class, args);
        LOG.info("Application started successfully.");
    }
}
