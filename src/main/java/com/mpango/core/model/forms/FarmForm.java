package com.mpango.core.model.forms;

import java.util.Date;

public class FarmForm {

	private Date DateCreated;
	private String FarmName;
	private int Size;
	private String Location;
	private String Description;

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

	@Override
	public String toString() {
		return "FarmForm [ DateCreated=" + DateCreated + ", FarmName=" + FarmName + ", Size=" + Size + ", Location="
				+ Location + ", Description=" + Description + "]";
	}

}
