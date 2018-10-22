package com.mpango.core.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Expense {
	private int id;
	private Date ExpenseDate;
	private int SupplierId;
	private String paidTo;
	private BigDecimal Amount;
	private int PaymentMethodId;
	private String PaymentMethod;
	private int AccountId;
	private String Account;
	private int ProjectId;
	private String ProjectName;
	private String FarmName;

	private int UserId;

	private String Notes;

	// ----------------------

	private String Supplier;

	// ----------------------

	public Expense() {
		id = 0;
	}

	public Expense(BigDecimal amount, int supplierId, int paymentMethodId, int accountId, int projectId,
			String notes, int userId) {
		ExpenseDate = new Date();
		Amount = amount;
		SupplierId = supplierId;
		PaymentMethodId = paymentMethodId;
		AccountId = accountId;
		ProjectId = projectId;
		Notes = notes;
		UserId = userId;
	}

	public Expense(BigDecimal amount, Date expenseDate, int supplierId, int paymentMethodId, int accountId,
			int projectId, String notes, int userId) {
		ExpenseDate = expenseDate;
		Amount = amount;
		SupplierId = supplierId;
		PaymentMethodId = paymentMethodId;
		AccountId = accountId;
		ProjectId = projectId;
		Notes = notes;
		UserId = userId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		this.UserId = userId;
	}

	public int getPaymentMethodId() {
		return PaymentMethodId;
	}

	public void setPaymentMethodId(int paymentMethodId) {
		PaymentMethodId = paymentMethodId;
	}

	public int getAccountId() {
		return AccountId;
	}

	public void setAccountId(int accountId) {
		AccountId = accountId;
	}

	public int getSupplierId() {
		return SupplierId;
	}

	public void setSupplierId(int supplierId) {
		SupplierId = supplierId;
	}

	public String getSupplier() {
		return Supplier;
	}

	public void setSupplier(String supplier) {
		Supplier = supplier;
	}

	public String getFarmName() {
		return FarmName;
	}

	public void setFarmName(String farmName) {
		FarmName = farmName;
	}

	public int getId() {
		return id;
	}

	public void setId(int id_) {
		id = id_;
	}

	public Date getExpenseDate() {
		return ExpenseDate;
	}

	public void setExpenseDate(Date expDate) {
		ExpenseDate = expDate;
	}

	public String getPaidTo() {
		return paidTo;
	}

	public void setPaidTo(String paidTo_) {
		paidTo = paidTo_;
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

	public String getProjectName() {
		return ProjectName;
	}

	public void setProjectName(String projectName) {
		ProjectName = projectName;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	@Override
	public String toString() {
		return "Expense xxx [id=" + id + ", ExpenseDate=" + ExpenseDate + ", SupplierId=" + SupplierId + ", Amount="
				+ Amount + ", PaymentMethodId=" + PaymentMethodId + ", AccountId=" + AccountId + ", ProjectId="
				+ ProjectId + ", Notes=" + Notes + ", UserId=" + UserId + "]";
	}

}
