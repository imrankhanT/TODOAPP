package com.bridgelabz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.DAO.UserDAO;
import com.bridgelabz.model.User;
import com.bridgelabz.utility.Email;
import com.bridgelabz.validation.EncryptPassword;

@Service
public class UserServiceImpl implements ServiceMethods {
	@Autowired
	UserDAO dao;

	@Transactional
	public boolean register(User user) {
		user.setPassword(EncryptPassword.encryptPassword(user.getPassword()));
		boolean found = dao.register(user);
		return found;
	}

	@Transactional
	public String login(User user) {
		user.setPassword(EncryptPassword.encryptPassword(user.getPassword()));
		String result = dao.login(user);
		return result;
	}

}
