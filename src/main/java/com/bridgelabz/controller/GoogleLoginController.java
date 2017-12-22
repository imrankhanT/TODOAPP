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

import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;
import com.bridgelabz.socialLogin.GoogleLogin;
import com.bridgelabz.utility.TokenGenerator;
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
	public void getGoogleToken(HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {
		String googleCode = request.getParameter("code");
		System.out.println("Google Code : " + googleCode);
		String googleAccessToken = GoogleLogin.googleAccessToken(googleCode);
		System.out.println("Google Acess Token : " + googleAccessToken);
		String googleProfile = GoogleLogin.getProfile(googleAccessToken);
		System.out.println("Get Profile : " + googleProfile);

		ObjectMapper mapper = new ObjectMapper();
		
			String email = mapper.readTree(googleProfile).get("email").asText();
			System.out.println("Email ; " + email);
			User user = service.getUserByEmail(email);

			if (user == null) {
				User googleUser = new User();
				googleUser.setEmail(email);
				String name = mapper.readTree(googleProfile).get("given_name").asText();
				googleUser.setName(name);

				String profilePic = mapper.readTree(googleProfile).get("picture").asText();
				googleUser.setProfilePicture(profilePic);

				System.out.println("Google User name : " + name);
				googleUser.setActive(true);
				boolean registerUser = service.register(googleUser);

				if (registerUser) {
					String token = TokenGenerator.generateToken(googleUser.getId());
					System.out.println("------------------->" + token);
					session.setAttribute("token", token);
					response.sendRedirect("http://localhost:8080/TODOAPP/#!/dummy");
				} else {
					response.sendRedirect("http://localhost:8080/TODOAPP/#!/login");
				}

			} else {
				System.out.println("User Already Registerd in the database.....");
				String token = TokenGenerator.generateToken(user.getId());
				user.setProfilePicture(mapper.readTree(googleProfile).get("picture").asText());
				service.updateUser(user);
				session.setAttribute("token", token);
				response.sendRedirect("http://localhost:8080/TODOAPP/#!/dummy");
			}
		} 
	

	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	public ResponseEntity<Response> getToken(HttpSession session) {
		Response response = new Response();
		response.setMessage((String) session.getAttribute("token"));
		session.removeAttribute("token");
		return ResponseEntity.ok(response);

	}
}
