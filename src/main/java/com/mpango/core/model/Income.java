package com.mpango.core.model;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Income {
	private int id;
	private Date IncomeDate;
	private int CustomerId;
	private String receivedFrom;
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

	private String Customer;

	// ----------------------

	public Income() {
		//id = 0;
	}

	public Income(BigDecimal amount, int customerId, int paymentMethodId, int accountId, int projectId, String notes, int userId) {
		IncomeDate = new Date();
		Amount = amount;
		CustomerId = customerId;
		PaymentMethodId = paymentMethodId;
		AccountId = accountId;
		ProjectId = projectId;
		Notes = notes;
		UserId = userId;
	}

	public Income(BigDecimal amount, Date incomeDate, int customerId, int paymentMethodId, int accountId,
			int projectId, String notes, int userId) {
		IncomeDate = incomeDate;
		Amount = amount;
		CustomerId = customerId;
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

	public int getCustomerId() {
		return CustomerId;
	}

	public void setCustomerId(int supplierId) {
		CustomerId = supplierId;
	}

	public String getCustomer() {
		return Customer;
	}

	public void setCustomer(String supplier) {
		Customer = supplier;
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

	public Date getIncomeDate() {
		return IncomeDate;
	}

	public void setIncomeDate(Date expDate) {
		IncomeDate = expDate;
	}

	public String getReceivedFrom() {
		return receivedFrom;
	}

	public void setPaidTo(String receivedFrom) {
		this.receivedFrom = receivedFrom;
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
		return "Income [id=" + id + ", IncomeDate=" + IncomeDate + ", CustomerId=" + CustomerId + ", Amount="
				+ Amount + ", PaymentMethodId=" + PaymentMethodId + ", AccountId=" + AccountId + ", ProjectId="
				+ ProjectId + ", Notes=" + Notes + ", UserId=" + UserId + "]";
	}

}
