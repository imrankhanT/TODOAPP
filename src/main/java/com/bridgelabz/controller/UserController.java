package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.ServiceMethods;
import com.bridgelabz.utility.Email;
import com.bridgelabz.utility.TokenGenerator;
import com.bridgelabz.validation.Validation;

@RestController
public class UserController {

	@Autowired
	ServiceMethods service;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> userRegister(@RequestBody User user, HttpServletResponse response,
			HttpServletRequest request) {

		if (Validation.isValid(user)) {
			boolean found = service.register(user);
			if (found == true) {
				String url = request.getRequestURL().toString();
				String token = TokenGenerator.generateToken(user.getId());
				url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token;
				Email.sendMail(user.getEmail(), "Validation Message", url);
				String message = "Register sucessfully......";
				return new ResponseEntity<String>(message, HttpStatus.CREATED);

			} else
				return new ResponseEntity<String>("email or phone number already exist", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("Invalid Credential", HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> login(@RequestBody User user, HttpSession session) {
		String name = service.login(user);
		System.out.println(name);
		if (name != null) {
			session.setAttribute("user", name);
			return new ResponseEntity<String>("welcome " + name + " Login Sucessfully....", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("mail id Or password Not Exist", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(HttpSession session) {
		session.removeAttribute("user");
		session.invalidate();
		return new ResponseEntity<String>("Logout sucessfully......", HttpStatus.OK);
	}
}
