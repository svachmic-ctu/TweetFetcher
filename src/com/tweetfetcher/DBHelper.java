package com.tweetfetcher;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class DBHelper {

	static Logger log = Logger.getLogger(TweetFetcher.class);
	private static final String PERSISTENCEUNIT="JPAService";
	private static EntityManagerFactory emfactory;

	static {
		log.trace("creating EntityManagerFactory");
		emfactory = Persistence.createEntityManagerFactory(PERSISTENCEUNIT);
	}

	static boolean createUser(String username, String nickname, Timestamp date) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);
		em.getTransaction().begin();

		User u = new User(username, nickname, date);

		em.persist(u);
		em.getTransaction().commit();
		return true;
	}

	static boolean createTweet(String username, String tweet, Timestamp date) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);
		em.getTransaction().begin();

		User user = getUser(username);
		Tweet t = new Tweet(user, tweet, date);

		em.persist(t);
		em.getTransaction().commit();
		return true;
	}

	static User getUser(String username) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.username='" + username + "'", User.class);
		User u = q.getSingleResult();
		
		return u;
	}

	static User findUser(int id) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.id=" + id, User.class);
		User u = q.getSingleResult();
		
		return u;
	}

	static List<User> listUsers() {
		List<User> result = new ArrayList<User>();
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> q = em.createQuery("SELECT u FROM User u", User.class);
		List<User> users = q.getResultList();

		for (User u : users) {
			result.add(u);
		}

		return result;
	}
	
	static List<Tweet> listTweets(User user) {
		List<Tweet> result = new ArrayList<Tweet>();
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<Tweet> q = em.createQuery("SELECT t FROM Tweet t WHERE t.user = :user", Tweet.class);
		q.setParameter("user", user);
		List<Tweet> tweets = q.getResultList();

		for (Tweet t : tweets) {
			result.add(t);
		}

		log.trace("All tweets from user " + user.getNick() + " retrieved.");

		return result;
	}

	static List<Tweet> listAll() {
		List<Tweet> result = new ArrayList<Tweet>();
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<Tweet> q = em.createQuery("SELECT t FROM Tweet t", Tweet.class);
		List<Tweet> tweets = q.getResultList();

		for (Tweet t : tweets) {
			result.add(t);
		}

		log.trace("All tweets from all users retrieved.");

		return result;
	}
	
	static void updateData() {
		
	}
}
