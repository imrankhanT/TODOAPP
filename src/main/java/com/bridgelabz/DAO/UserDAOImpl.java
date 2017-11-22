package com.bridgelabz.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import com.bridgelabz.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	SessionFactory sessionFactory;

	@RequestMapping()
	public boolean register(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from User where email = :email or mobileNumber = :number");
		query.setParameter("email", user.getEmail());
		query.setParameter("number", user.getMobileNumber());
		User checkedUser = (User) query.uniqueResult();

		if (checkedUser != null) {
			return false;

		} else {
			session.persist(user);
			return true;
		}
	}

	public String login(User user) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("select name from User where email = :email or password = :password");
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		String name = (String) query.uniqueResult();

		if (name != null)
			return name;
		return null;
	}

}
