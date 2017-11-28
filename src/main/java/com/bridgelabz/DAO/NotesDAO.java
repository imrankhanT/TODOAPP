package com.bridgelabz.DAO;

import java.util.List;

import com.bridgelabz.model.Notes;

public interface NotesDAO {
	public String addNotes(Notes notes);

	public void updateNotes(Notes notes);

	public int deleteNotes(int id);

	public List<Notes> getAllNotes();

	public Notes getNotesById(int id);

}
