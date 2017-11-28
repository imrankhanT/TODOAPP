package com.bridgelabz.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	SessionFactory sessionFactory;

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
		Query<?> query = session.createQuery("select name from User where email = :email and password = :password");
		query.setParameter("email", user.getEmail());
		query.setParameter("password", user.getPassword());
		String name = (String) query.uniqueResult();

		if (name != null)
			return name;
		return null;
	}

	public User getUserById(int id) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from User where id=:id");
		query.setParameter("id", id);
		User user = (User) query.uniqueResult();
		return user;
	}

	public int userActive(int id, User user) {
		Session session = sessionFactory.getCurrentSession();

		Query<?> query = session.createQuery("update User set isActive =:isActive where id =:id");
		query.setParameter("id", id);
		query.setParameter("isActive", true);

		int i = query.executeUpdate();
		if (i > 0)
			return i;
		else
			return 0;
	}

	public int getUserByMail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("select id from User where email = :email");
		query.setParameter("email", email);
		Integer id = (Integer) query.uniqueResult();
		return id;
	}

	public boolean checkActiveUser(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("select isActive from User where email =:email");
		query.setParameter("email", email);
		Boolean isActive = (Boolean) query.uniqueResult();
		return isActive;
	}

	public User forgotPassword(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from User where email =:email");
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}

	public int updatePassword(int id, User user) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("update User set password =:password where id =:id");
		query.setParameter("password", user.getPassword());
		query.setParameter("id", id);
		System.out.println(id + "------------------->" + user.getPassword());
		int count = query.executeUpdate();
		if (count != 0)
			return count;
		else
			return 0;
	}

	public User getUserByEmail(String email) {
		Session session = sessionFactory.getCurrentSession();
		Query<?> query = session.createQuery("from User where email =:email");
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}

}
