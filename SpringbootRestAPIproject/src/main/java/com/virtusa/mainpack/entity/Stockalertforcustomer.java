package com.virtusa.mainpack.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stockalertforcustomer {

	@Id
	private String custuname;
	
	@Column( name="product_name" , nullable=false, unique = true, updatable= true)
	private String prodname;
	
	@Column( name="orderquantity" , updatable=true)
	private int orderquant;

	public Stockalertforcustomer() {
		super();
	}

	@Override
	public String toString() {
		return "Stockalertforcustomer [custuname=" + custuname + ", prodname=" + prodname + ", orderquant=" + orderquant
				+ "]";
	}

	public Stockalertforcustomer(String custuname, String prodname, int orderquant) {
		super();
		this.custuname = custuname;
		this.prodname = prodname;
		this.orderquant = orderquant;
	}

	public String getCustuname() {
		return custuname;
	}

	public void setCustuname(String custuname) {
		this.custuname = custuname;
	}

	public String getProdname() {
		return prodname;
	}

	public void setProdname(String prodname) {
		this.prodname = prodname;
	}

	public int getOrderquant() {
		return orderquant;
	}

	public void setOrderquant(int orderquant) {
		this.orderquant = orderquant;
	}
}
