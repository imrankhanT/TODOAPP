package com.bridgelabz.controller;

import java.sql.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
import com.bridgelabz.service.NotesService;

@RestController
public class NotesController {

	@Autowired
	NotesService note;

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
		response.setToken(token);
		if (isSaved != null)
			return new ResponseEntity<Response>(response, HttpStatus.CREATED);
		else
			return new ResponseEntity<Response>(response, HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/updateNotes", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateNotes(@RequestBody Notes notes) {
		Notes retNotes = note.getNotesById(notes.getId());
		if (retNotes == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("notes not found.....");
		} else {
			
			notes.setModificationDate(new Date(System.currentTimeMillis()));
			notes.setUser(retNotes.getUser());
			notes.setCreationDate(retNotes.getCreationDate());
			
			note.updateNote(notes);
			return ResponseEntity.status(HttpStatus.OK).body("Updated Sucessfullly.....");
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
	public ResponseEntity<List<Notes>> getAllNotes() {
		System.out.println("Hi......");
		List<Notes> list = note.getAllNotes();

		if (list == null) {
			return new ResponseEntity<List<Notes>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Notes>>(list, HttpStatus.OK);

	}
}
