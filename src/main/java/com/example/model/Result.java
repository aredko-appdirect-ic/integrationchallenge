package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "result" )
public class Result {
	private boolean success;
	private String message;
	private int errorCode;
	
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
	
	public static Result successful( final String message ) {
		final Result result = new Result();
		result.setSuccess( true );
		result.setMessage( message );
		result.setErrorCode( 0 );
		return result;
	}
	
	public static Result fail( final Throwable ex, final int errorCode ) {
		final Result result = new Result();
		result.setSuccess( false );
		result.setMessage( ex.getMessage() );
		result.setErrorCode( errorCode );
		return result;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}
}
