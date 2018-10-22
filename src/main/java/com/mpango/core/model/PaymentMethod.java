package com.mpango.core.model;

import java.util.Date;

public class PaymentMethod {

	private int id;
	private String PaymentMethod;
	private String Description;
	private Date DateCreated;

	public PaymentMethod() {
		id = 0;
	}
	
	public PaymentMethod(String paymentMethod, String description) {
		PaymentMethod = paymentMethod;
		Description = description;
		DateCreated = new Date();
	}

	public PaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
		DateCreated = new Date();
	}
	
	

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPaymentMethod() {
		return PaymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}

	public Date getDateCreated() {
		return DateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		DateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Expense [id=" + id + ", PaymentMethod=" + PaymentMethod + ", DateCreated=" + DateCreated + "]";
	}
}
