package com.bridgelabz.service;

import java.util.List;
import java.util.Set;

import com.bridgelabz.model.Labels;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public interface NotesService {
	public String addNotes(Notes notes, String token);

	public void updateNote(Notes notes);

	public int deleteNotes(int id);

	public List<Notes> getAllNotes(int id);

	public Notes getNotesById(int id);
	
	public boolean insertLable(Labels labels);
	
	public int deleteLabels(int id);
	
	public List<Labels> getAllLables(User user);
}
