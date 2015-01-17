package com.splitshare.db.model;

public class FinanceModel {
	private int _id;
	private float amountOwed;
	private String reason;
	private int fromID;
	private int toID;
	
	public FinanceModel() {
		//Empty constructor
	}
	
	public FinanceModel(int amountOwed, String reason, int fromID, int toID) {
		this.amountOwed = amountOwed;
		this.reason = reason;
		this.fromID = fromID;
		this.toID = toID;
	}
	
	public FinanceModel(int id, int amountOwed, String reason, int fromID, int toID) {
		this._id = id;
		this.amountOwed = amountOwed;
		this.reason = reason;
		this.fromID = fromID;
		this.toID = toID;
	}
	
	// Getters and Setters
	public int getID() {
		return this._id;
	}
	
	public void setID(int id) {
		this._id = id;
	}
	
	public float getAmountOwed() {
		return this.amountOwed;
	}
	
	public void setAmountOwed(float amountOwed) {
		this.amountOwed = amountOwed;
	}
	
	public String getReason() {
		return this.reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}
	
	public int getFromID() {
		return this.fromID;
	}
	
	public void setFromID(int id) {
		this.fromID = id;
	}
	
	public int getToID() {
		return this.toID;
	}
	
	public void setToID(int id) {
		this.toID = id;
	}
}