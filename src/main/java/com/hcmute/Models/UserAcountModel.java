package com.hcmute.Models;

import java.io.Serializable;

public class UserAcountModel implements Serializable{

	private static final long serialVersionUID = 1L;
	private String userName;
	private String email;
	private String pass;
	private String checkPass;
	private boolean remember;
	public UserAcountModel(String userName, String email, String pass, String checkPass, boolean remember) {
		this.userName = userName;
		this.email = email;
		this.pass = pass;
		this.checkPass = checkPass;
		this.remember = remember;
	}
	public UserAcountModel() {
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getCheckPass() {
		return checkPass;
	}
	public void setCheckPass(String checkPass) {
		this.checkPass = checkPass;
	}
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
	
	
	
	
	

}
