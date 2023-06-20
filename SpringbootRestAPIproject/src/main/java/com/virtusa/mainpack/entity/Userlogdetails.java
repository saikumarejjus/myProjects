package com.virtusa.mainpack.entity;


import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;



//@Data
@Entity
@Component
public class Userlogdetails implements Serializable{

	
	public Userlogdetails() {
		super();
	}

	@Override
	public String toString() {
		return "Adminlogdetails [username=" + username +  ", fullname=" + fullname
				+ ", userpassword=" + userpassword + ", usermail=" + usermail + ", userphno=" + userphno + ", roles="
				+ roles + "]";
	}

	public Userlogdetails(
			@Size(min = 3) @Pattern(regexp = "[a-z]+", message = "name can have only upper and lowercase characters") String username,
			@Size(min = 3) @Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters") String fullname,
			@Size(min = 8) String userpassword, @Email String usermail,
			@Pattern(regexp = "[6-9][\\d]{9}", message = "name can have only numbers and first number should start with more than 5") String userphno,
			Admrole roles) {
		super();
		this.username = username;
	
		this.fullname = fullname;
		this.userpassword = userpassword;
		this.usermail = usermail;
		this.userphno = userphno;
		this.roles = roles;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getUserpassword() {
		return userpassword;
	}

	public void setUserpassword(String userpassword) {
		this.userpassword = userpassword;
	}

	public String getUsermail() {
		return usermail;
	}

	public void setUsermail(String usermail) {
		this.usermail = usermail;
	}

	public String getUserphno() {
		return userphno;
	}

	public void setUserphno(String userphno) {
		this.userphno = userphno;
	}

	public Admrole getRoles() {
		return roles;
	}

	public void setRoles(Admrole roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Size(min=3) 
	@NotNull
	@Pattern(regexp = "[a-z]+", message = "name can have only lowercase characters")
	private String username;
	
	@Size(min=3)  
	@Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters")
	private String fullname;
	
	@Size(min=8) 
	private String userpassword;
	
	@Email
	private String usermail;
	
	@Pattern(regexp = "[6-9][\\d]{9}", message = "name can have only numbers and first number should start with more than 5")
	private String userphno;
	
	
	@OneToOne(cascade=CascadeType.ALL)
	private Admrole roles;
	
	
	
}
