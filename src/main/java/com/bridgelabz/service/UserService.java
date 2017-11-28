package com.bridgelabz.service;

import com.bridgelabz.model.User;

public interface UserService {
	public boolean register(User user);

	public String login(User user);

	public User getUserById(int id);

	public int activeUser(int id, User user);

	public int getUserByMail(String email);

	public boolean checkUserActive(String email);

	public User forgotPassword(String email);

	public int updatePassword(int id, User user);
	
	public User getUserByEmail(String email);

}
