package com.virtusa.mainpack.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import org.springframework.stereotype.Component;



//@Data
//@JsonFilter("prodbeanfilter")
@Entity
@Component
public class Productdetails implements Serializable{

	public Productdetails() {
		super();
	}

	

	
	public Productdetails( int productid,
			@NotNull @Size(min = 3) @Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters") String productname,
			@Min(5) int productprice,  int productsavalible, Date date) {
		super();
		
		this.productid = productid;
		this.productname = productname;
		this.productprice = productprice;
		this.productsavalible = productsavalible;
		this.date = date;
	}


	@Override
	public String toString() {
		return "Productdetails [ productid=" + productid + ", productname=" + productname
				+ ", productprice=" + productprice + ", productsavalible=" + productsavalible + ", date=" + date + "]";
	}

	

	public int getProductid() {
		return productid;
	}

	public void setProductid(int productid) {
		this.productid = productid;
	}

	public String getProductname() {
		return productname;
	}

	public void setProductname(String productname) {
		this.productname = productname;
	}

	public int getProductprice() {
		return productprice;
	}

	public void setProductprice(int productprice) {
		this.productprice = productprice;
	}

	public int getProductsavalible() {
		return productsavalible;
	}

	public void setProductsavalible(int productsavalible) {
		this.productsavalible = productsavalible;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	
	
	@Id
	private int productid;
	
	@NotNull
	@Size(min=3) 
	@Pattern(regexp = "[a-zA-Z ]+", message = "name can have only upper and lowercase characters")
	private String productname;
	
	@Min(5)
	private int productprice;
	
	@Min(10)
	private int productsavalible;
	
	@Temporal(TemporalType.DATE)
	private Date date = new Date(System.currentTimeMillis());
	
 
	

}