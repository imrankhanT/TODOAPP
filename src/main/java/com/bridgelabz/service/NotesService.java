package com.bridgelabz.service;

import java.util.List;

import com.bridgelabz.model.Notes;

public interface NotesService {
	public String addNotes(Notes notes, String token);

	public void updateNote(Notes notes);

	public int deleteNotes(int id);

	public List<Notes> getAllNotes(int id);

	public Notes getNotesById(int id);
   
}
