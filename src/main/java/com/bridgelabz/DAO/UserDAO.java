package com.bridgelabz.DAO;

import com.bridgelabz.model.Notes;
import com.bridgelabz.model.User;

public interface UserDAO {
	public boolean register(User user);

	public String login(User user);

	public User getUserById(int id);

	public int userActive(int id, User user);

	public int getUserByMail(String email);

	public User forgotPassword(String email);

	public boolean checkActiveUser(String email);

	public int updatePassword(int id, User user);

	public User getUserByEmail(String email);
	
}
