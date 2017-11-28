package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.socialLogin.FBConnection;
import com.bridgelabz.utility.TokenGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class FBLoginController {
	@Autowired
	UserService service;

	@RequestMapping(value = "/facebookLogin", method = RequestMethod.GET)
	public ResponseEntity<Void> fbLogin(HttpServletRequest request, HttpServletResponse response) {
		String fbUrl = FBConnection.getFBURL();

		try {
			response.sendRedirect(fbUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Void>(HttpStatus.OK);
	}

	@RequestMapping(value = "/getFacebookLogin", method = RequestMethod.GET)
	public ResponseEntity<String> getFbToken(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session = request.getSession();
		String fbCode = request.getParameter("code");
		System.out.println("code: " + fbCode);
		String fbtoken = FBConnection.getFBAcessToken(fbCode);
		System.out.println("token is: " + fbtoken);
		String fbProfileInfo = FBConnection.getFbProfileInfo(fbtoken);
		System.out.println("fbProfileInfo: " + fbProfileInfo);
		ObjectMapper mapper = new ObjectMapper();
		try {
			String email = mapper.readTree(fbProfileInfo).get("email").asText();
			User user = service.getUserByEmail(email);
			if (user == null) {
				User fbUser = new User();
				fbUser.setEmail(email);
				String firstName = mapper.readTree(fbProfileInfo).get("first_name").asText();
				fbUser.setName(firstName);

				fbUser.setActive(true);
				service.register(fbUser);
				int id = fbUser.getId();

				if (id == -1) {
				} else {
					String accessToken = TokenGenerator.generateToken(id);
					session.setAttribute("ToDoAccessToken", accessToken);
				}
			} else {
				String accessToken = TokenGenerator.generateToken(user.getId());
				session.setAttribute("ToDoAccessToken", accessToken);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("new Registered");

	}
}
