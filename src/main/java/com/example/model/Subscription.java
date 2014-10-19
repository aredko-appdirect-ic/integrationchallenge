package com.example.model;

public class Subscription {
	private String id;
	private String email;
	private String firstName;
	private String lastName;
	
	public Subscription() {	
	}
	
	public Subscription( final String id ) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
