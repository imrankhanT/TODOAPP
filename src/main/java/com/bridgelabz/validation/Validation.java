package com.bridgelabz.validation;

import com.bridgelabz.model.User;

public class Validation {
	public static boolean isValid(User user) {
		boolean valid = true;
		String phoneNumRegx = "^[0-9]{10}$";
		String nameRegx = "^[a-zA-Z]{4,}(?: [a-zA-Z]+){0,2}$";
		String emailRegx = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		if (!(user.getName()).matches(nameRegx)) {
			System.out.println("user");
			valid = false;
		} else

		if (!user.getMobileNumber().matches(phoneNumRegx)) {
			System.out.println("number");
			valid = false;
		} else

		if (!user.getEmail().matches(emailRegx)) {
			System.out.println("email");
			valid = false;
		} else if (user.getPassword().length() < 7 || user.getPassword().length() > 16) {
			System.out.println("length");
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

	public static boolean checkPassword(User user) {
		if (user.getPassword().length() < 7 || user.getPassword().length() > 16) {
			System.out.println("length");
			return false;
		}
		return true;
	}
}
