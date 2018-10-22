package com.mpango.core.model.forms;

import java.math.BigDecimal;

public class ExpenseForm {
	private int ExpenseId;
	private int UserId;
	private String ExpenseDate;
	private int SupplierId;
	private String SupplierNames;
	private BigDecimal Amount;
	private int PaymentMethodId;
	private String PaymentMethod;
	private int AccountId;
	private String Account;
	private int ProjectId;
	private String Notes;

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getExpenseId() {
		return ExpenseId;
	}

	public void setExpenseId(int expenseId) {
		ExpenseId = expenseId;
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

	public String getSupplierNames() {
		return SupplierNames;
	}

	public void setSupplierNames(String supplierNames) {
		SupplierNames = supplierNames;
	}

	public String getExpenseDate() {
		return ExpenseDate;
	}

	public void setExpenseDate(String expenseDate) {
		ExpenseDate = expenseDate;
	}

	public int getSupplierId() {
		return SupplierId;
	}

	public void setSupplierId(int supplier) {
		SupplierId = supplier;
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
		return "ExpenseForm [ ExpenseDate=" + ExpenseDate + ", SupplierNames=" + SupplierNames + ", Amount=" + Amount
				+ ", PaymentMethod=" + PaymentMethod + ", Account=" + Account + ", Enterprise=" + ProjectId + ", Notes="
				+ Notes + "]";
	}

}
