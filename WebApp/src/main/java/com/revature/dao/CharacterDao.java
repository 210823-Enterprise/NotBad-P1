package com.revature.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.util.HibernateUtil;

public class CharacterDao {

	public int insert(Character chr) {
		
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		int pk = (int)	ses.save(chr);
		tx.commit();
		return pk;
	}
	
	public List<Character> selectAll() {
		Session ses = HibernateUtil.getSession();
		List<Character> characters = ses.createQuery("from Character", Character.class).list();
		
		return characters;
	}
	
}