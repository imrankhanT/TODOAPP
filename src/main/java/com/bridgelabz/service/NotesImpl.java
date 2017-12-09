package com.bridgelabz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.DAO.NotesDAO;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;
import com.bridgelabz.utility.TokenGenerator;

@Service
public class NotesImpl implements NotesService {

	@Autowired
	NotesDAO dao;

	@Transactional
	public String addNotes(Notes notes, String token) {
		int id = TokenGenerator.verfiyToken(token);
		String isSaved;
		if (id > 0) {
			User user = new User();
			user.setId(id);
			notes.setUser(user);
			isSaved = dao.addNotes(notes);
			if (isSaved != null)
				return "Notes Created Sucessfully";
			else
				return "Notes Not Created ......";
		} else {
			return "Id Not Exsists......";
		}
	}

	@Transactional
	public void updateNote(Notes notes) {
		dao.updateNotes(notes);
	}

	@Transactional
	public int deleteNotes(int id) {
		int count = dao.deleteNotes(id);
		return count;
	}

	@Transactional
	public List<Notes> getAllNotes(int id) {
		List<?> notes = dao.getAllNotes(id);
		return (List<Notes>) notes;
	}

	@Transactional
	public Notes getNotesById(int id) {
		Notes notes = dao.getNotesById(id);
		return notes;
	}

	@Transactional
	public void updateColor(Notes notes, int userId) {
		dao.updateColor(notes, userId);
	}

	@Transactional
	public void updatePin(Notes notes, int userId) {
		dao.updatePin(notes, userId);
	}

	@Transactional
	public void updateTrash(Notes notes, int userId) {
		dao.updateTrash(notes, userId);
	}

	@Transactional
	public void updateArchive(Notes notes, int userId) {
		dao.updateArchive(notes, userId);
	}
}
