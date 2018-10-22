package com.mpango.core.model;

import java.util.Date;

public class Supplier {

	private int Id;
	private Date DateCreated;
	private int userID;
	private String SupplierNames;
	private String Description;

	public Supplier() {
		Id = 0;
	}
	
	public Supplier(Date date, int userID, String names, String description) {
		DateCreated = date;
		this.userID = userID;
		SupplierNames = names;
		Description = description;
	}

	public String getDescription() {
		return Description;
	}



	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public void setDescription(String description) {
		Description = description;
	}



	public int getId() {
		return Id;
	}

	public void setId(int id) {
		this.Id = id;
	}

	public String getSupplierNames() {
		return SupplierNames;
	}

	public void setSupplierNames(String Names) {
		SupplierNames = Names;
	}

	
}
