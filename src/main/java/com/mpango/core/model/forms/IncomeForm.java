package com.mpango.core.model.forms;

import java.math.BigDecimal;

public class IncomeForm {
	private int IncomeId;
	private int UserId;
	private String IncomeDate;
	private int CustomerId;
	private String CustomerNames;
	private BigDecimal Amount;
	private int PaymentMethodId;
	private String PaymentMethod;
	private int AccountId;
	private String Account;
	private int ProjectId;
	private String Notes;
	
	

	public int getIncomeId() {
		return IncomeId;
	}

	public void setIncomeId(int incomeId) {
		IncomeId = incomeId;
	}

	public String getIncomeDate() {
		return IncomeDate;
	}

	public void setIncomeDate(String incomeDate) {
		IncomeDate = incomeDate;
	}

	public int getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(int customerId) {
		CustomerId = customerId;
	}

	public String getCustomerNames() {
		return CustomerNames;
	}

	public void setCustomerNames(String customerNames) {
		CustomerNames = customerNames;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getAccountId() {
		return AccountId;
	}

	public void setAccountId(int accountId) {
		AccountId = accountId;
	}

	public int getPaymentMethodId() {
		return PaymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		PaymentMethodId = paymentMethodId;
	}

	public BigDecimal getAmount() {
		return Amount;
	}

	public void setAmount(BigDecimal amt) {
		Amount = amt;
	}

	public String getPaymentMethod() {
		return PaymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}

	public String getAccount() {
		return Account;
	}

	public void setAccount(String acct) {
		Account = acct;
	}

	public int getProjectId() {
		return ProjectId;
	}

	public void setProjectId(int entr) {
		ProjectId = entr;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	@Override
	public String toString() {
		return "ExpenseForm [ IncomeDate=" + IncomeDate + ", CustomerNames=" + CustomerNames + ", Amount=" + Amount
				+ ", PaymentMethod=" + PaymentMethod + ", Account=" + Account + ", Enterprise=" + ProjectId + ", Notes="
				+ Notes + "]";
	}

}
