package com.mpango.core.model.forms;

import java.util.Date;

public class SupplierForm {

	private Date DateCreated;
	private String SupplierName;
	private String Description;
	
	

	public String getSupplierName() {
		return SupplierName;
	}

	public void setSupplierName(String supplierName) {
		SupplierName = supplierName;
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
		return "FarmForm [ DateCreated=" + DateCreated + ", SupplierName=" + SupplierName +  ", Description=" + Description + "]";
	}

}
