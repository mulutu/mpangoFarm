package com.mpango.core.model.forms;

import java.util.Date;

public class PayMethodForm {

	private Date DateCreated;
	private String PayMethodName;
	private String Description;
	
	

	public String getPayMethodName() {
		return PayMethodName;
	}

	public void setPayMethodName(String payMethodName) {
		PayMethodName = payMethodName;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}


	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	@Override
	public String toString() {
		return "FarmForm [ DateCreated=" + DateCreated + ", PayMethodName=" + PayMethodName +  ", Description=" + Description + "]";
	}

}
