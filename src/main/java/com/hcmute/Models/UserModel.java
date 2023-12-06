package com.hcmute.Models;

import java.io.Serializable;

public class UserModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private long userID;
	private String userName;
	public UserModel(long userID, String userName) {

		this.userID = userID;
		this.userName = userName;
	}
	public UserModel() {

	}
	public long getUserID() {
		return userID;
	}
	public void setUserID(long userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
