package com.amazingcode.in.example.response;

import org.springframework.http.HttpStatusCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
	
	private int statusCode;
    private String message;
    private HttpStatusCode status;

}