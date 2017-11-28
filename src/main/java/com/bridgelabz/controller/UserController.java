package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.utility.Email;
import com.bridgelabz.utility.TokenGenerator;
import com.bridgelabz.validation.EncryptPassword;
import com.bridgelabz.validation.Validation;

@RestController
public class UserController {

	@Autowired
	UserService service;

	public static int id;

	@RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> userRegister(@RequestBody User user, HttpServletRequest request) {
		Response response = new Response();
		if (Validation.isValid(user)) {
			boolean found = service.register(user);
			if (found == true) {
				String url = request.getRequestURL().toString();
				String token = TokenGenerator.generateToken(user.getId());
				url = url.substring(0, url.lastIndexOf("/")) + "/active/" + token;
				System.out.println(url);
				Email.sendMail(user.getEmail(), "Validation Message", url);
				response.setMessage("Register sucessfully......");
				return new ResponseEntity<Response>(response, HttpStatus.CREATED);

			} else {
				response.setMessage("email or phone number already exist");
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			}
		} else {
			response.setMessage("Invalid Credential");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);

		}
	}

	@RequestMapping(value = "/active/{jwt:.+}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> verifyToken(@PathVariable String jwt) {
		int id = TokenGenerator.verfiyToken(jwt);
		Response response = new Response();
		System.out.println("Token verfied id is......" + id);
		if (id > 0) {
			User user = service.getUserById(id);
			if (user == null) {
				response.setMessage("User Not Found");
				return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
			}
			service.activeUser(id, user);
			response.setMessage("User Activated");
			response.setToken(jwt);
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
		response.setMessage("Invalid User Id");
		return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> login(@RequestBody User user, HttpSession session) {
		Response response = new Response();
		String name = service.login(user);
		System.out.println(name);
		if (name != null) {

			if (service.checkUserActive(user.getEmail())) {
				session.setAttribute("user", name);
				int id = service.getUserByMail(user.getEmail());
				String token = TokenGenerator.generateToken(id);
				response.setMessage("welcome " + name + " Login Sucessfully....");
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			} else {
				response.setMessage("User is InActive Please! Verfiy your email to activate User");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			}
		} else {
			response.setMessage("mail id Or password Not Exist");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> logout(HttpSession session) {
		Response response = new Response();
		session.removeAttribute("user");
		session.invalidate();
		response.setMessage("Logout sucessfully......");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public ResponseEntity<Response> forgotPassword(@RequestBody User users, HttpServletRequest request,
			HttpSession session) {
		Response response = new Response();
		User user = service.forgotPassword(users.getEmail());
		if (user == null) {
			response.setMessage("User Not Register With Mail");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {

			String token = TokenGenerator.generateToken(user.getId());
			int id = TokenGenerator.verfiyToken(token);
			session.setAttribute("UserId", id);
			System.out.println(id);
			String url = request.getRequestURL().toString();

			url = url.substring(0, url.lastIndexOf("/")) + "/setPassword/" + token;
			Email.sendMail(users.getEmail(), "Password Reset", url);
			response.setMessage("Email verified	Sucessfully......");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.PUT)
	public ResponseEntity<Response> resetPassword(@RequestBody User user, HttpSession session) {
		Response response = new Response();
		if (!Validation.checkPassword(user)) {
			response.setMessage("Please Enter the password between 7 to 16 charcters");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {
			user.setPassword(EncryptPassword.encryptPassword(user.getPassword()));
			int id = (Integer) session.getAttribute("UserId");
			user.setId(id);
			int count = service.updatePassword(id, user);
			if (count == 0) {
				response.setMessage("Password Not Updated.......");
				return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
			} else {
				response.setMessage("Reset Password Sucessfully........");
				return new ResponseEntity<Response>(response, HttpStatus.OK);
			}

		}
	}

	@RequestMapping(value = "/setPassword/{token:.+}", method = RequestMethod.GET)
	public ResponseEntity<String> setPassword(@PathVariable String token) throws Exception {
		int id = TokenGenerator.verfiyToken(token);
		System.out.println(id);
		if (id > 0) {
			return ResponseEntity.status(HttpStatus.OK).body("Set new Password sucessfully.....");
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
	}
}
