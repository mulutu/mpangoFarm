package com.mpango.core.model.forms;

import java.util.Date;

public class COAForm {

	private Date DateCreated;
	private String AccountName;
	private String AccountCode;
	private int AccountTypeId;
	private int userId;
	private String Description;
	
	public Date getDateCreated() {
		return DateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}
	public String getAccountName() {
		return AccountName;
	}
	public void setAccountName(String accountName) {
		AccountName = accountName;
	}
	public String getAccountCode() {
		return AccountCode;
	}
	public void setAccountCode(String accountCode) {
		AccountCode = accountCode;
	}
	public int getAccountTypeId() {
		return AccountTypeId;
	}
	public void setAccountTypeId(int accountTypeId) {
		AccountTypeId = accountTypeId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	
	

}
