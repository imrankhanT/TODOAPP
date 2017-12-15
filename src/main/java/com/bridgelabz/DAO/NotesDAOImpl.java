package com.bridgelabz.DAO;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Notes;

@Repository
public class NotesDAOImpl implements NotesDAO {

	@Autowired
	SessionFactory sessionFactory;

	public String addNotes(Notes notes) {
		Session session = sessionFactory.getCurrentSession();

		try {
			session.save(notes);
			return "saved";
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public void updateNotes(Notes notes) {
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(notes);
	}

	public int deleteNotes(int id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("delete Notes where id =:id");
			query.setParameter("id", id);
			int count = query.executeUpdate();
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public Notes getNotesById(int id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("from Notes where id =:id");
			query.setParameter("id", id);
			Notes notes = (Notes) query.uniqueResult();
			return notes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Notes> getAllNotes(int User_Id) {
		Session session = sessionFactory.getCurrentSession();

		try {
			Query<?> query = session.createQuery("from Notes where User_Id =:User_Id");
			query.setParameter("User_Id", User_Id);
			List<Notes> allNotes = (List<Notes>) query.list();
			System.out.println(allNotes);
			return allNotes;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
