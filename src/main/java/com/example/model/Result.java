package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "result" )
public class Result {
	private boolean success;
	private String message;
	
	public boolean isSuccess() {
		return success;
	}
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public static Result successful() {
		final Result result = new Result();
		result.setSuccess( true );
		return result;
	}
}
