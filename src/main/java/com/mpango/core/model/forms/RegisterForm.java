package com.mpango.core.model.forms;

public class RegisterForm {

	private String Email;
	private String Password;

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	@Override
	public String toString() {
		return "RegisterForm [ Email=" + Email + ", Password=" + Password + "]";
	}

}
