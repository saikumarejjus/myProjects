package com.virtusa.mainpack.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;


//@Data
@Entity
public class Admrole implements Serializable{

	@Override
	public String toString() {
		return "Admrole [roleid=" + roleid + ", rolename=" + rolename + "]";
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public String getRolename() {
		return rolename;
	}
	public void setRolename(String rolename) {
		this.rolename = rolename;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private static final long serialVersionUID = 1L;
	public Admrole() {
		super();
	}
	public Admrole(int roleid, String rolename) {
		super();
		this.roleid = roleid;
		this.rolename = rolename;
	}
	
	@Id
	
	private int roleid;
	private String rolename;
}
