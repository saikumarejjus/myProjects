package com.virtusa.mainpack.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customerorderstatus {
		
	@Id
	private int orderid;
	private String custorderstatus;
	
	@Override
	public String toString() {
		return "Orderstatus [orderid=" + orderid + ", orderstatus=" + custorderstatus + "]";
	}
	public Customerorderstatus() {
		super();
	}
	public Customerorderstatus(int orderid, String orderstatus) {
		super();
		this.orderid = orderid;
		this.custorderstatus = orderstatus;
	}
	public int getOrderid() {
		return orderid;
	}
	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}
	public String getOrderstatus() {
		return custorderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.custorderstatus = orderstatus;
	}

}
