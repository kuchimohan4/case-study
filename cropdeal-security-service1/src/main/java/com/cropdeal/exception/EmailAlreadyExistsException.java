package com.cropdeal.exception;


public class EmailAlreadyExistsException extends RuntimeException {
    
	public EmailAlreadyExistsException(String message) {
        super(message);
    }
}