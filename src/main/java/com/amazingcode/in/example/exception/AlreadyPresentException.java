package com.amazingcode.in.example.exception;

import lombok.Getter;

@Getter
public class AlreadyPresentException extends RuntimeException {
	
	private final String exceptionMessage;

	public AlreadyPresentException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

}