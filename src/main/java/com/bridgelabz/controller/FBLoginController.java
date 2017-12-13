package com.bridgelabz.controller;

import java.io.IOException;

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
import com.bridgelabz.socialLogin.FBConnection;
import com.bridgelabz.utility.TokenGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
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
	public void getFbToken(HttpServletRequest request, HttpServletResponse response) throws JsonProcessingException, IOException {
		HttpSession session = request.getSession();
		String fbCode = request.getParameter("code");
		System.out.println("code: " + fbCode);
		String fbtoken = FBConnection.getFBAcessToken(fbCode);
		System.out.println("token is: " + fbtoken);
		String fbProfileInfo = FBConnection.getFbProfileInfo(fbtoken);
		System.out.println("Fburl-->" + fbProfileInfo);
		Response responseMessage = new Response();
		ObjectMapper mapper = new ObjectMapper();
	
			String email = mapper.readTree(fbProfileInfo).get("email").asText();
			User user = service.getUserByEmail(email);
			if (user == null) {
				User fbUser = new User();
				fbUser.setEmail(email);
				String firstName = mapper.readTree(fbProfileInfo).get("first_name").asText();
				fbUser.setName(firstName);

				String fbProfilePic = mapper.readTree(fbProfileInfo).get("picture").get("data").get("url").asText();
				System.out.println("profilePic......................." + fbProfilePic);
				fbUser.setActive(true);
				fbUser.setProfilePicture(fbProfilePic);
				boolean flag=service.register(fbUser);
				if(flag) {
				String token = TokenGenerator.generateToken(fbUser.getId());
				System.out.println("------------------->"+token);
				session.setAttribute("token", token);
				response.sendRedirect("http://localhost:8080/TODOAPP/#!/dummy");
				}
				else
				{
					response.sendRedirect("http://localhost:8080/TODOAPP/#!/login");
				}

			} else {
				System.out.println("user already exits in database");
				String token = TokenGenerator.generateToken(user.getId());
				user.setProfilePicture(mapper.readTree(fbProfileInfo).get("picture").get("data").get("url").asText());
				service.updateUser(user);
				session.setAttribute("token", token);
				response.sendRedirect("http://localhost:8080/TODOAPP/#!/dummy");
			}
			
		
	}

	@RequestMapping(value = "/dummy", method = RequestMethod.GET)
	public ResponseEntity<Response> getToken(HttpSession session) {


		Response response = new Response();
		response.setMessage((String)session.getAttribute("token"));
		session.removeAttribute("token");
		return  ResponseEntity.ok(response);
		
	}
}
