package com.virtusa.mainpack.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.virtusa.mainpack.controller.Logincontroller;

//@Data
@Entity
public class Customercart {

	@Id
	@GeneratedValue
	private int productcartno;
	
	public int getProductcartno() {
		return productcartno;
	}

	public void setProductcartno(int productcartno) {
		this.productcartno = productcartno;
	}

	@NotNull(message= "enter the poduct name correctly")
	private String prodname;
	
	@Min(1)
	@Max(20)
	private int noquant;

	@NotNull
	private String username = Logincontroller.getUsername();

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public int getNoquant() {
		return noquant;
	}

	public void setNoquant(int noquant) {
		this.noquant = noquant;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Customercart() {
		super();
	}

	public Customercart(@NotNull(message = "enter the poduct name correctly") String prodname,
			@Min(1) @Max(20) int noquant, @NotNull String username) {
		super();
		this.prodname = prodname;
		this.noquant = noquant;
		this.username = username;
	}

	@Override
	public String toString() {
		return "Customercart [productcartno=" + productcartno + ",prodname=" + prodname + ", noquant=" + noquant + ", username=" + username + "]";
	}

	
	
}

