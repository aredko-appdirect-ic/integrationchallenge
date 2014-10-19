package com.example.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement( name = "result" )
public class Result {
	private boolean success;
	private String message;
	private String errorCode;
	private String accountIdentifier;
	
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
		return result;
	}
	
	public static Result fail( final Throwable ex, final int errorCode ) {
		final Result result = new Result();
		result.setSuccess( false );
		result.setMessage( ex.getMessage() );
		result.setErrorCode( "UNKNOWN_ERROR" );
		return result;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getAccountIdentifier() {
		return accountIdentifier;
	}

	public void setAccountIdentifier(String accountIdentifier) {
		this.accountIdentifier = accountIdentifier;
	}
	
	public Result withAccountIdentifier( final String accountIdentifier ) {
		setAccountIdentifier( accountIdentifier );
		return this;
	}
}
