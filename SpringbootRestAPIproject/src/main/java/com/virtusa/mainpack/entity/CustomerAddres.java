package com.virtusa.mainpack.entity;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
public class CustomerAddres   {
	


	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	 
	private  int adrid;
	
	private  String doorno;
	
	private String streetname;
	
	private String cityname;
	
	private String districtname;
	
	private String  state;
	
	private int pincode;
	
	 @OneToMany(mappedBy ="custaddres" ,cascade = CascadeType.ALL)
	 @JsonManagedReference
	public List<CustomerorderDetails> custord;
	
	
	public CustomerAddres() {
		super();
	}


	public int getAdrid() {
		return adrid;
	}


	public void setAdrid(int adrid) {
		this.adrid = adrid;
	}


	public String getDoorno() {
		return doorno;
	}


	public void setDoorno(String doorno) {
		this.doorno = doorno;
	}


	public String getStreetname() {
		return streetname;
	}


	public void setStreetname(String streetname) {
		this.streetname = streetname;
	}


	public String getCityname() {
		return cityname;
	}


	public void setCityname(String cityname) {
		this.cityname = cityname;
	}


	public String getDistrictname() {
		return districtname;
	}


	public void setDistrictname(String districtname) {
		this.districtname = districtname;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public int getPincode() {
		return pincode;
	}


	public void setPincode(int pincode) {
		this.pincode = pincode;
	}


	public List<CustomerorderDetails> getCustord() {
		return custord;
	}


	public void setCustord(List<CustomerorderDetails> custord) {
		this.custord = custord;
	}





	public CustomerAddres(String doorno, String streetname, String cityname, String districtname, String state,
			int pincode, List<CustomerorderDetails> custord) {
		super();
		this.doorno = doorno;
		this.streetname = streetname;
		this.cityname = cityname;
		this.districtname = districtname;
		this.state = state;
		this.pincode = pincode;
		this.custord = custord;
	}


	@Override
	public String toString() {
		return "CustomerAddres [adrid=" + adrid + ", doorno=" + doorno + ", streetname=" + streetname + ", cityname="
				+ cityname + ", districtname=" + districtname + ", state=" + state + ", pincode=" + pincode
				+ ", custord=" + custord + "]";
	}
	

}










