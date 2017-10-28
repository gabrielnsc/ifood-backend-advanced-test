package com.ifood.challenge.musicadvisor.vo;

public class ServerError {
	private String message;

	public ServerError(String message) {
		this.message = message;
	}

	public ServerError(Exception ex) {
		this.message = ex.getMessage();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
