package com.amazingcode.in.example.exception;

import lombok.Getter;

@Getter
public class NotPresentException extends RuntimeException {
	
	private final String exceptionMessage;

	public NotPresentException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

}