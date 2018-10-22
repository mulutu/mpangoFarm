package com.mpango.core.model.forms;

import java.util.Date;

public class CustomerForm {

	private Date DateCreated;
	private String CustomerNames;
	private String Description;
	
	
	

	public String getCustomerNames() {
		return CustomerNames;
	}

	public void setCustomerNames(String customerNames) {
		CustomerNames = customerNames;
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
		return "CustomerForm [ DateCreated=" + DateCreated + ", CustomerNames=" + CustomerNames +  ", Description=" + Description + "]";
	}

}
