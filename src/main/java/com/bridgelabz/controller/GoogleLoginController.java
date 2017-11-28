package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.socialLogin.GoogleLogin;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class GoogleLoginController {
	@Autowired
	private UserService service;

	@RequestMapping(value = "/googleLogin", method = RequestMethod.GET)
	public void googleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String googleUrl = GoogleLogin.genrateUrl();
		System.out.println(googleUrl);
		try {
			response.sendRedirect(googleUrl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/getGoogleToken", method = RequestMethod.GET)
	public ResponseEntity<String> getGoogleToken(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String googleCode = request.getParameter("code");
		System.out.println("Google Code : " + googleCode);
		String googleAccessToken = GoogleLogin.googleAccessToken(googleCode);
		System.out.println("Google Acess Token : " + googleAccessToken);
		String googleProfile = GoogleLogin.getProfile(googleAccessToken);
		System.out.println("Get Profile : " + googleProfile);

		ObjectMapper mapper = new ObjectMapper();
		try {
			String email = mapper.readTree(googleProfile).get("email").asText();
			System.out.println("Email ; " + email);
			User user = service.getUserByEmail(email);

			if (user == null) {
				User googleUser = new User();
				googleUser.setEmail(email);
				String name = mapper.readTree(googleProfile).get("given_name").asText();
				googleUser.setName(name);
				System.out.println("Google User name : " + name);
				googleUser.setActive(true);
				service.register(googleUser);
				int id = googleUser.getId();
				System.out.println("Google user id : " + id);

				if (id == 0) {
					return ResponseEntity.status(HttpStatus.OK).body("Id Not Exsists....");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(HttpStatus.OK).body("new Registered");
	}
}
