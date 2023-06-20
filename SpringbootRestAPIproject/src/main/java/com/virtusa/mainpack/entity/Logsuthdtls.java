package com.virtusa.mainpack.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;



//@Data
@Component
public class Logsuthdtls {

	@NotNull(message="enter correct username")
	private String username;
	
	@NotBlank(message="enter correct password ")
	private String password;
	
	@Override
	public String toString() {
		return "Logsuthdtls [username=" + username + ", password=" + password + "]";
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Logsuthdtls(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public Logsuthdtls() {
		super();
	}
	
}
