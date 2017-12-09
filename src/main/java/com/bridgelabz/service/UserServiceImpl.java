package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.DAO.UserDAO;
import com.bridgelabz.model.User;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDAO dao;

	@Transactional
	public boolean register(User user) {
		boolean found = dao.register(user);
		return found;
	}

	@Transactional
	public String login(User user) {
		String result = dao.login(user);
		return result;
	}

	@Transactional
	public User getUserById(int id) {
		User user = dao.getUserById(id);
		return user;
	}

	@Transactional
	public int activeUser(int id, User user) {
		int i = dao.userActive(id, user);
		return i;
	}

	@Transactional
	public int getUserByMail(String email) {
		int id = dao.getUserByMail(email);
		return id;
	}

	@Transactional
	public boolean checkUserActive(String email) {
		boolean isActive = dao.checkActiveUser(email);
		return isActive;
	}

	@Transactional
	public User forgotPassword(String email) {
		User user = dao.forgotPassword(email);
		return user;
	}

	@Transactional
	public int updatePassword(int id, User user) {
		int users = dao.updatePassword(id, user);
		return users;
	}

	@Transactional
	public User getUserByEmail(String email) {
		User user = dao.getUserByEmail(email);
		return user;
	}
}
