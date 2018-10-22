package com.mpango.core.model.forms;

import java.util.Date;

public class ProjectForm {

	private Date DateCreated;
	private String ProjectName;
	private int FarmID;
	private String Description;

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public int getFarmID() {
		return FarmID;
	}

	public void setFarmID(int farmID) {
		FarmID = farmID;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public String toString() {
		return "FarmForm [ DateCreated=" + DateCreated + ", ProjectName=" + ProjectName + ", FarmID=" + FarmID + ", Description=" + Description + "]";
	}

}
