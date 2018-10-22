package com.mpango.core.model;

public class COAAccountType {
	private int id;

	private String AccountTypeName;	
	private String AccountTypeCode;	
	private int AccountGroupTypeId;	

	public COAAccountType() {
		id = 0;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAccountTypeName() {
		return AccountTypeName;
	}

	public void setAccountTypeName(String accountTypeName) {
		AccountTypeName = accountTypeName;
	}

	public String getAccountTypeCode() {
		return AccountTypeCode;
	}

	public void setAccountTypeCode(String accountTypeCode) {
		AccountTypeCode = accountTypeCode;
	}

	public int getAccountGroupTypeId() {
		return AccountGroupTypeId;
	}

	public void setAccountGroupTypeId(int accountGroupTypeId) {
		AccountGroupTypeId = accountGroupTypeId;
	}		
	
	

}
