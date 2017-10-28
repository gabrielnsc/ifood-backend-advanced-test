package com.ifood.challenge.musicadvisor.exception;

public class InvalidCityException extends Exception {
	private static final long serialVersionUID = -1109630245255280448L;

	public InvalidCityException(String message) {
		super(message);
	}
	
	public InvalidCityException(Exception ex) {
		super(ex);
	}
}
