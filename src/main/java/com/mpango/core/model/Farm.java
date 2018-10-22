package com.mpango.core.model;

import java.util.Date;

public class Farm {

	private int id;
	private Date DateCreated;
	private String FarmName;
	private int Size;
	private String Location;
	private String Description;
	private int UserId;
	
	public Farm() {
		id = 0;
	}


	public Farm(String farmName, int size, String location, String description, int userId) {
		DateCreated = new Date();
		FarmName = farmName;
		Size = size;
		Location = location;
		Description = description;
		UserId = userId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public String getFarmName() {
		return FarmName;
	}

	public void setFarmName(String farmName) {
		FarmName = farmName;
	}

	public int getSize() {
		return Size;
	}

	public void setSize(int size) {
		Size = size;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

}
