package com.mocProject.shop.exeptionHandling;

public class entityNotFoundException extends RuntimeException {


	public entityNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public entityNotFoundException(String message) {
		super(message);
	}

	public entityNotFoundException(Throwable cause) {
		super(cause);
	}

	
}
