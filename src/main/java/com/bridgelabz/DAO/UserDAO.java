package com.bridgelabz.DAO;

import com.bridgelabz.model.User;

public interface UserDAO {
	public boolean register(User user);

	public String login(User user);

}
