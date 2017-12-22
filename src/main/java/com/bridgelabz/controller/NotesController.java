package com.bridgelabz.controller;

import java.sql.Date;
import java.util.List;

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

import com.bridgelabz.model.Notes;
import com.bridgelabz.model.Response;
import com.bridgelabz.model.User;
import com.bridgelabz.model.UserDTO;
import com.bridgelabz.service.NotesService;
import com.bridgelabz.service.UserService;
import com.bridgelabz.utility.TokenGenerator;

@RestController
public class NotesController {

	@Autowired
	NotesService note;

	@Autowired
	UserService userService;

	@RequestMapping(value = "/addNotes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> saveNotes(@RequestBody Notes notes, HttpServletRequest request) {
		Response response = new Response();
		Date date = new Date(System.currentTimeMillis());
		notes.setCreationDate(date);
		notes.setModificationDate(date);
		String token = request.getHeader("accToken");
		System.out.println(token);
		String isSaved = note.addNotes(notes, token);
		response.setMessage(isSaved);
		if (isSaved != null)
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		else
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "updateNotes", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> updateNotes(@RequestBody Notes notes1) {
    System.out.println("Notes--------->"+notes1.getNotePicture());
		Notes retNotes = note.getNotesById(notes1.getId());
		Response response = new Response();
		if (retNotes == null) {
			response.setMessage("Notes Not Found...");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {

			notes1.setModificationDate(new Date(System.currentTimeMillis()));
			notes1.setUser(retNotes.getUser());

			note.updateNote(notes1);
			response.setMessage("Update Sucessfully.......");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteNotes/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> deleteNotes(@PathVariable int id) {
		Response response = new Response();
		System.out.println("hi......");
		int count = note.deleteNotes(id);
		if (count != 0) {
			response.setMessage("Id Not Exsist");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setMessage("Deleted Sucessfully....");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getNotesById/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notes> getNotesById(@PathVariable int id) {
		Notes notes = note.getNotesById(id);
		System.out.println("------------------>" + id);
		if (notes == null) {
			return new ResponseEntity<Notes>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Notes>(notes, HttpStatus.OK);
	}

	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET)
	public ResponseEntity<List<Notes>> getAllNotes(HttpServletRequest request) {
		System.out.println("Hi......");
		String token = request.getHeader("accToken");
		System.out.println("Token---------> " + token);
		int id = TokenGenerator.verfiyToken(token);
		System.out.println("id--------->" + id);
		List<Notes> list = note.getAllNotes(id);

		if (list == null) {
			return new ResponseEntity<List<Notes>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Notes>>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpServletRequest request, HttpSession session) {
		System.out.println("inside get user");
		String token = request.getHeader("accToken");
		session.setAttribute("token", token);
		int id = TokenGenerator.verfiyToken(token);
		System.out.println("inside get user token ==" + id);
		User user = userService.getUserById(id);
		/* System.out.println(user.getId()); */

		if (user == null) {
			Response response = new Response();
			response.setMessage("User Not Found......");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}

		else {
			UserDTO userDto = new UserDTO();
			userDto.setEmail(user.getEmail());
			userDto.setProfilePic(user.getProfilePicture());
			userDto.setName(user.getName());
			return new ResponseEntity<UserDTO>(userDto, HttpStatus.OK);
		}

	}

/*	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response> logout(HttpSession session, HttpServletResponse response1) {
		Response response = new Response();
		session.removeAttribute("token");
		session.invalidate();
		try {
			response1.sendRedirect("http://localhost:8080/TODOAPP/#!/login");
		} catch (IOException e) {
			e.printStackTrace();
		}
		response.setMessage("Logout sucessfully......");
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}*/
}
