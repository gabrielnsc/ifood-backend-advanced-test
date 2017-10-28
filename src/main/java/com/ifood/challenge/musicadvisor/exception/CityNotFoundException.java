package com.ifood.challenge.musicadvisor.exception;

public class CityNotFoundException extends Exception {
	private static final long serialVersionUID = -1109630245255280448L;

	public CityNotFoundException(String message) {
		super(message);
	}
	
	public CityNotFoundException(Exception ex) {
		super(ex);
	}
}
