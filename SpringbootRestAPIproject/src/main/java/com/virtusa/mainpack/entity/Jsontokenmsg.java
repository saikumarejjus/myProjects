package com.virtusa.mainpack.entity;



//@Data
public class Jsontokenmsg {

	public String getJsontokenmsg() {
		return jsonmesg;
	}

	public void setJsontokenmsg(String jsontokenmesg) {
		this.jsonmesg = jsontokenmesg;
	}

	@Override
	public String toString() {
		return "jsonmesg [jsonmsg=" + jsonmesg + "]";
	}

	private String jsonmesg;

	public Jsontokenmsg(String jsontokenmesg) {
		super();
		this.jsonmesg = jsontokenmesg;
	}

	public Jsontokenmsg() {
		super();
	}
	
}
