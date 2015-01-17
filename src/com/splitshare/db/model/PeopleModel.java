package com.splitshare.db.model;

import java.util.List;
import java.util.Locale;

import android.content.Context;

import com.splitshare.db.DatabaseHelper;

public class PeopleModel {
	private int _id;
	private String firstName;
	private String lastName;
	private int isUser;
	
	public PeopleModel() {
		//Empty constructor
	}
	
	public PeopleModel(String firstName, String lastName, int isUser) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.isUser = isUser;
	}
	
	public PeopleModel(int id, String firstName, String lastName, int isUser) {
		this._id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.isUser = isUser;
	}
	
	// Getters and Setters
	public int getID() {
		return this._id;
	}
	
	public int getID(Context context) {
		DatabaseHelper dbHelper = new DatabaseHelper(context);
		List<PeopleModel> people = dbHelper.getAllPeople();
		
		for (PeopleModel p : people) {
			if (p.getFirstName().toLowerCase(Locale.getDefault())
					.equals(firstName.toLowerCase(Locale.getDefault()))
					&& p.getLastName().toLowerCase(Locale.getDefault())
							.equals(lastName.toLowerCase(Locale.getDefault()))) {
				return p.getID();
			}
		}
		
		return -1;
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
	
	public int getIsUser() {
		return this.isUser;
	}
	
	public void setIsUser(int isUser) {
		this.isUser = isUser;
	}
}