package com.choreographydemoproj.securityservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;






@Entity
public class UserLoginDetails{
	
	public UserLoginDetails() {
		super();
	}

	@Id
	@GeneratedValue
	private String username;
	
	 private String fullName;
	
	 private String userPassword;
	
	 private String userEmail;
	
	 private String userPhoneNo;

	 private String userRole;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPhoneNo() {
		return userPhoneNo;
	}

	public void setUserPhoneNo(String userPhoneNo) {
		this.userPhoneNo = userPhoneNo;
	}

	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	public UserLoginDetails(String username, String fullName, String userPassword, String userEmail, String userPhoneNo, String userRole) {
		this.username = username;
		this.fullName = fullName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhoneNo = userPhoneNo;
		this.userRole = userRole;
	}

	@Override
	public String toString() {
		return "UserLoginDetails{" +
				"username='" + username + '\'' +
				", fullName='" + fullName + '\'' +
				", userPassword='" + userPassword + '\'' +
				", userEmail='" + userEmail + '\'' +
				", userPhoneNo='" + userPhoneNo + '\'' +
				", userRole='" + userRole + '\'' +
				'}';
	}
}
