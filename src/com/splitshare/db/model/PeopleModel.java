package com.splitshare.db.model;

public class PeopleModel {
	private int _id;
	private String firstName;
	private String lastName;
	private boolean isUser;
	
	public PeopleModel() {
		//Empty constructor
	}
	
	public PeopleModel(String firstName, String lastName, boolean isUser) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.isUser = isUser;
	}
	
	public PeopleModel(int id, String firstName, String lastName, boolean isUser) {
		this._id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isUser = isUser;
	}
	
	// Getters and Setters
	public int getID() {
		return this._id;
	}
	
	public void setID(int id) {
		this._id = id;
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return this.lastName;
	}
	
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public boolean getIsUser() {
		return this.isUser;
	}
	
	public void setIsUser(boolean isUser) {
		this.isUser = isUser;
	}
}