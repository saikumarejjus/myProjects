package com.virtusa.mainpack.entity;


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;




//@Data
@Entity
public class OrderProductsforStore {

	public OrderProductsforStore() {
		super();
	}

	public OrderProductsforStore( int productid, @Min(5) int productcost, @Min(20) int productcount,
			@Size(min = 3) @Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters") String sellername,
			@Email String selleremail, int totalordercost) {
		super();
		
		this.productid = productid;
		this.productcost = productcost;   
		this.productcount = productcount;
		this.sellername = sellername;
		this.selleremail = selleremail;
		this.totalordercost = totalordercost;
		
	}


	@Override
	public String toString() {
		return "OrderProductsforStore [sno=" + sno + ", orderid=" + orderid + ", productid=" + productid
				+ ", productcost=" + productcost + ", productcount=" + productcount + ", sellername=" + sellername
				+ ", selleremail=" + selleremail + ", totalordercost=" + totalordercost + ", date=" + date + "]";
	}

	public int getOrderid() {
		return orderid;
	}

	public int getSno() {
		return sno;
	}

	public void setSno(int sno) {
		this.sno = sno;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public int getProductcost() {
		return productcost;
	}

	public void setProductcost(int productcost) {
		this.productcost = productcost;
	}

	public int getProductcount() {
		return productcount;
	}

	public void setProductcount(int productcount) {
		this.productcount = productcount;
	}

	public String getSellername() {
		return sellername;
	}

	public void setSellername(String sellername) {
		this.sellername = sellername;
	}

	public String getSelleremail() {
		return selleremail;
	}

	public void setSelleremail(String selleremail) {
		this.selleremail = selleremail;
	}

	public int getTotalordercost() {
		return totalordercost;
	}

	public void setTotalordercost(int totalordercost) {
		this.totalordercost = totalordercost;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	

	@Id
	@GeneratedValue
	private  int sno;
	
	private  int orderid;
	
	@NotNull
	private  int productid;
	
	@Min(5)
	private int productcost;
	
	@Min(20)
	@Max(500)
	private int productcount;
	
	@Size(min=3) 
	@Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters")
	private String sellername;
	
	@Email
	private  String selleremail;
	
	private int totalordercost;
	
	@Temporal(TemporalType.DATE)
	private Date date = new Date(System.currentTimeMillis());
	
	


	
	
}
