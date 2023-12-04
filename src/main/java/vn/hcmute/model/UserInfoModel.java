package vn.hcmute.model;

import java.sql.Date;

public class UserInfoModel {
	private String fullName;
	private Date dateOfBirth;
	private String avata;
	private String phoneNumber;
	public UserInfoModel(String fullName, Date dateOfBirth, String avata, String phoneNumber) {
		this.fullName = fullName;
		this.dateOfBirth = dateOfBirth;
		this.avata = avata;
		this.phoneNumber = phoneNumber;
	}
	public UserInfoModel() {
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAvata() {
		return avata;
	}
	public void setAvata(String avata) {
		this.avata = avata;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
}
