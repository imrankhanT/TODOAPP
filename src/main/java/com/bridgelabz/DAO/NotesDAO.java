package com.bridgelabz.DAO;

import java.util.List;

import com.bridgelabz.model.Labels;
import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public interface NotesDAO {
	public String addNotes(Notes notes);

	public void updateNotes(Notes notes);

	public int deleteNotes(int id);

	public List<Notes> getAllNotes(int id);

	public Notes getNotesById(int id);

	public boolean insertLabel(Labels labels);

	public int deletLabels(int id);
	
	public List<Labels> getAllLabels(User user);
	
	public Labels getLabelById(int id);
	
	public void updateLable(Labels labels);
}
