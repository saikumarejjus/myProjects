package com.virtusa.mainpack.entity;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

//@Data
@Entity
@Component
public class CustomerorderDetails  {

	
	public CustomerorderDetails( int orderid, String customername, String poductname, int poductprice,
			int poductquanity) {
		super();
	
		this.orderid = orderid;
		this.customername = customername;
		this.poductname = poductname;
		this.poductprice = poductprice;
		this.poductquanity = poductquanity;
	}
		

	@Override
	public String toString() {
		return "CustomerorderDetails [sno=" + sno + ", orderid=" + orderid + ", customername=" + customername
				+ ", poductname=" + poductname + ", poductprice=" + poductprice + ", poductquanity=" + poductquanity
				+ ", totalordervalue=" + totalordervalue + ", orderdate=" + orderdate + ", custaddres=" + custaddres + " ]";
	}


	public int getSno() {
		return sno;
	}


	public void setSno(int sno) {
		this.sno = sno;
	}


	public int getOrderid() {
		return orderid;
	}


	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}


	public String getCustomername() {
		return customername;
	}


	public void setCustomername(String customername) {
		this.customername = customername;
	}


	public String getPoductname() {
		return poductname;
	}


	public void setPoductname(String poductname) {
		this.poductname = poductname;
	}


	public int getPoductprice() {
		return poductprice;
	}


	public void setPoductprice(int poductprice) {
		this.poductprice = poductprice;
	}


	public int getPoductquanity() {
		return poductquanity;
	}


	public void setPoductquanity(int poductquanity) {
		this.poductquanity = poductquanity;
	}


	public int getTotalordervalue() {
		return totalordervalue;
	}


	public void setTotalordervalue(int totalordervalue) {
		this.totalordervalue = totalordervalue;
	}


	public Date getOrderdate() {
		return orderdate;
	}


	public void setOrderdate(Date orderdate) {
		this.orderdate = orderdate;
	}


	public CustomerAddres getCustaddres() {
		return custaddres;
	}


	public void setCustaddres(CustomerAddres custaddres) {
		this.custaddres = custaddres;
	}


	public CustomerorderDetails() {
		super();
	}
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int sno;
	private int orderid;
	private String customername;
    private String poductname;
    private int  poductprice;
    private int  poductquanity;
    private int totalordervalue;
    @Temporal(TemporalType.DATE)
	private Date orderdate = new Date(System.currentTimeMillis());
    
    @ManyToOne 
    @JsonBackReference	 
    private CustomerAddres custaddres;
    
   
    
  
	
}
