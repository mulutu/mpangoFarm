package com.mpango.core.model;

import java.util.Date;

public class Customer {

	private int Id;
	private Date DateCreated;
	private int userID;
	private String CustomerNames;
	private String Description;

	public Customer() {
		Id = 0;
	}
	
	public Customer(Date date, int userID, String names, String description) {
		DateCreated = date;
		this.userID = userID;
		CustomerNames = names;
		Description = description;
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

	public String getDescription() {
		return Description;
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

	public String getCustomerNames() {
		return CustomerNames;
	}

	public void setCustomerNames(String names) {
		CustomerNames = names;
	}

}
