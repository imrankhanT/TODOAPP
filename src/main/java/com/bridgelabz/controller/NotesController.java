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

import com.bridgelabz.model.Labels;
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

	@RequestMapping(value = "/updateNotes", method = RequestMethod.POST)
	public ResponseEntity<Response> updateNotes(@RequestBody Notes notes1) {
		System.out.println("Update Notes--------->" + notes1);
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
		int count = note.deleteNotes(id);
		if (count != 0) {
			response.setMessage("Id Not Exsist");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setMessage("Deleted Sucessfully....");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/getAllNotes", method = RequestMethod.GET)
	public ResponseEntity<List<Notes>> getAllNotes(HttpServletRequest request) {
		String token = request.getHeader("accToken");
		int id = TokenGenerator.verfiyToken(token);
		List<Notes> list = note.getAllNotes(id);
		if (list == null) {

			return new ResponseEntity<List<Notes>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Notes>>(list, HttpStatus.OK);

	}

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpServletRequest request, HttpSession session) {
		String token = request.getHeader("accToken");
		session.setAttribute("token", token);
		int id = TokenGenerator.verfiyToken(token);
		User user = userService.getUserById(id);

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

	@RequestMapping(value = "/getOwner", method = RequestMethod.POST)
	public ResponseEntity<?> getOwnerOfNotes(@RequestBody Notes notes) {
		Notes notes2 = note.getNotesById(notes.getId());
		User user = notes2.getUser();
		Response response = new Response();
		if (user == null) {
			response.setMessage("User Not Found......");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		} else {
			response.setMessage("User Found...");
			response.setEmail(user.getEmail());
			response.setName(user.getName());
			response.setProfilePic(user.getProfilePicture());
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "shareNotes/{notesId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> insertSharedId(@PathVariable int notesId, HttpServletRequest request) {
		String token = request.getHeader("accToken");
		int id = TokenGenerator.verfiyToken(token);
		System.out.println("id-->" + id);
		Notes notes2 = note.getNotesById(notesId);
		User user2 = userService.getUserByEmail(request.getHeader("email"));
		Response response = new Response();

		if (id > 0) {
			notes2.getUserId().add(user2);
			note.updateNote(notes2);
			response.setMessage("User Found....");
			response.setEmail(user2.getEmail());
			response.setProfilePic(user2.getProfilePicture());
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("Invalid Token..........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/deleteCollaborator", method = RequestMethod.POST)
	public ResponseEntity<?> deleteCollaborator(@RequestBody Notes notes, HttpServletRequest request) {
		Notes notes2 = note.getNotesById(notes.getId());
		User user = userService.getUserByEmail(request.getHeader("accToken"));
		Response response = new Response();

		if (user != null) {
			notes2.getUserId().remove(user);
			note.updateNote(notes2);
			response.setMessage("User Removed Sucessfully.......");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("USer Not Found");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/insertLabel/{labels}", method = RequestMethod.POST)
	public ResponseEntity<?> insertLabel(@PathVariable String labels, HttpServletRequest request) {
		int id = TokenGenerator.verfiyToken(request.getHeader("accToken"));
		User user = userService.getUserById(id);
		Labels labels2 = new Labels();
		labels2.setLabelName(labels);
		labels2.setUser(user);
		labels2.setNotes(null);
		Response response = new Response();
		boolean isInsert = note.insertLable(labels2);
		if (isInsert) {
			response.setMessage("Labels Inserted Sucessfully..........");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("Insertion Fails.........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/deleteLabels/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLabels(@PathVariable int id) {
		System.out.println("DeleteLabel..........." + id);
		Response response = new Response();
		int count = note.deleteLabels(id);

		if (count > 0) {
			response.setMessage("Lables Deleted Sucessfully......");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("Labels Not Found......");
			return new ResponseEntity<Response>(response, HttpStatus.NOT_FOUND);
		}

	}

	@RequestMapping(value = "/getAllLabels", method = RequestMethod.GET)
	public ResponseEntity<List<Labels>> getAllLabels(HttpServletRequest request) {
		int id = TokenGenerator.verfiyToken(request.getHeader("accToken"));
		User user = userService.getUserById(id);

		List<Labels> lables = note.getAllLables(user);

		if (lables == null) {
			return new ResponseEntity<List<Labels>>(HttpStatus.NOT_FOUND);
		} else {
			return new ResponseEntity<List<Labels>>(lables, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/updateLabel/{id}/{labelName}", method = RequestMethod.PUT)
	public ResponseEntity<?> updateLabel(@PathVariable int id, @PathVariable String labelName,
			HttpServletRequest request) {
		Response response = new Response();
		System.out.println("LabelName----------->" + labelName);
		Labels label = note.getLabelById(id);
		String token = request.getHeader("accToken");
		int ids = TokenGenerator.verfiyToken(token);
		User user = userService.getUserById(ids);
		System.out.println("User-------->" + user.getName());
		label.setLabelName(labelName);
		label.setUser(user);
		label.setNotes(null);
		if (ids > 0) {
			note.updateLabel(label);
			response.setMessage("Label Updated Sucessfully.....");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("Label Not Found........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value = "/updateNotesLabels/{noteId}/{labelId}", method = RequestMethod.POST)
	public ResponseEntity<?> updateNotesLabels(@PathVariable int noteId, @PathVariable int labelId,
			HttpServletRequest request) {
		System.out.println("UpdateNotesLabels--------------------------------------------->");
		Response response = new Response();
		Notes notes = note.getNotesById(noteId);
		Labels labels = note.getLabelById(labelId);
		int id = TokenGenerator.verfiyToken(request.getHeader("accToken"));
		User user = userService.getUserById(id);
		if (note != null) {
			labels.setId(labelId);
			user.setId(id);
			notes.getUserId().add(user);
			notes.getLables().add(labels);
			note.updateNote(notes);
			response.setMessage("Notes Id");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		} else {
			response.setMessage("Notes And Labels Updates");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/deleteLabels/{notesId}/{labelId}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNotesLabels(@PathVariable int notesId, @PathVariable int labelId) {
		Response response = new Response();
		Notes notes = note.getNotesById(notesId);
		Labels labels = note.getLabelById(labelId);

		if (note != null) {
			notes.getLables().remove(labels);
			note.updateNote(notes);
			response.setMessage("Labels Removed Sucessfully from Notes");
			return new ResponseEntity<Response>(response, HttpStatus.OK);
		} else {
			response.setMessage("Notes Not Found.........");
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
		}
	}
}
