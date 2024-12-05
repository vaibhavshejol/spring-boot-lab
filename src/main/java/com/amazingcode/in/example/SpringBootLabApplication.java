package com.amazingcode.in.example;

import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootLabApplication {
	
	private static final Logger LOG = Logger.getLogger(SpringBootLabApplication.class.getName());

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLabApplication.class, args);
		LOG.info("Hello World!");
	}

}
