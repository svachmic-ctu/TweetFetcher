package com.tweetfetcher.server;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class DBHelper {

	static Logger log = Logger.getLogger(TweetFetcher.class);

	private static final int TWEETS_MAX = 10;
	private static final String PERSISTENCEUNIT = "JPAService";
	private static EntityManagerFactory emfactory;

	static {
		log.trace("creating EntityManagerFactory");
		emfactory = Persistence.createEntityManagerFactory(PERSISTENCEUNIT);
	}

	static boolean createUser(String name, String nick, Timestamp date, String picture) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		em.getTransaction().begin();
		User u = new User(name, nick, date, picture);
		em.persist(u);
		em.getTransaction().commit();
		return true;
	}

	static boolean createTweet(String nick, String tweet, Timestamp date) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		em.getTransaction().begin();
		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.nick = :nick" , User.class);
		q.setParameter("nick", nick);
		User u = q.getSingleResult();
		Tweet t = new Tweet(u, tweet, date);
		em.persist(t);
		em.getTransaction().commit();
		return true;
	}

	static User findUser(String nick) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> q = em.createQuery("SELECT u FROM User u WHERE u.nick = '" + nick + "'", User.class);

		try {
			User u = q.getSingleResult();
			return u;
		} catch (Exception e) {
			if (e.getClass() == NoResultException.class) {
				log.info("User @" + nick + " not found.");
				return new User();
			} else if (e.getClass() == NonUniqueResultException.class) {
				log.error("User @" + nick + " duplicated in the database, fix it manually.");
				return null;
			} else {
				log.error("Cannot get user from the database.");
				return null;
			}
		}
	}
	
	static Tweet findTweet(String nick, Timestamp date) {
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> uq = em.createQuery("SELECT u FROM User u WHERE u.nick = :nick", User.class);
		uq.setParameter("nick", nick);
		User u = uq.getSingleResult();
		TypedQuery<Tweet> tq = em.createQuery("SELECT t FROM Tweet t WHERE t.user = :user AND t.date = :date", Tweet.class);
		tq.setParameter("user", u);
		tq.setParameter("date", date);
		
		try {
			Tweet t = tq.getSingleResult();
			return t;
		} catch (Exception e) {
			if (e.getClass() == NoResultException.class) {
				log.info("Tweet of user @" + nick + "from " + date + " not found.");
				return new Tweet();
			} else if (e.getClass() == NonUniqueResultException.class) {
				log.error("Tweet of user @" + nick + " duplicated in the database, fix it manually.");
				return null;
			} else {
				log.error("Cannot get tweet from the database.");
				return null;
			}
		}
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
	
	static List<Tweet> listTweets(int id) {
		List<Tweet> result = new ArrayList<Tweet>();
		EntityManager em = emfactory.createEntityManager();
		log.trace("EntityManager = " + em);

		TypedQuery<User> uq = em.createQuery("SELECT u FROM User u WHERE u.id = " + id , User.class);
		User user = uq.getSingleResult();
		TypedQuery<Tweet> tq = em.createQuery("SELECT t FROM Tweet t WHERE t.user = :user", Tweet.class);
		tq.setParameter("user", user);
		List<Tweet> tweets = tq.getResultList();

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
	
	static int updateData() {
		int cnt = 0;
		Tweet tweet;
		Timestamp date;

		List<User> users = listUsers();
		if (users.isEmpty()) {
			return cnt;
		}

		try {
			// Connect to twitter API via twitter4j.properties file.
			Twitter twitter = TwitterFactory.getSingleton();

			// Set the paging to list the last 10 tweets.
		    Paging paging = new Paging(1, TWEETS_MAX);

		    for (User user : users) {
	
				List<Status> statuses = twitter.getUserTimeline(user.getNick(), paging);
				for (Status s : statuses) {
					date = new Timestamp(s.getCreatedAt().getTime());

					// Trying to find the user in the database.
					if ((tweet = findTweet(user.getNick(), date)) != null) {

						// Creating new tweet if the tweet is returned empty.
						if (tweet.getTweet() == null) {

							// Creating a new tweet based on retrieved values
							// from Twitter API.
							createTweet(user.getNick(), s.getText(), date);
							cnt++;
						}
					}
				}
		    }
		} catch (TwitterException te) {
			te.printStackTrace();
			log.error("Failed to get tweets: " + te.getMessage());
			return cnt;
		}

		log.info(cnt + " new tweets added into the database");
		return cnt;
	}

	static int addUser(String nick) {
		int cnt = 0;
		User user;
		Tweet tweet;
		Timestamp date;

		try {
			// Connect to twitter API via twitter4j.properties file.
			Twitter twitter = TwitterFactory.getSingleton();

			// Set the paging to list the last 10 tweets.
		    Paging paging = new Paging(1, TWEETS_MAX);

	    		// Trying to find the user in the database.
	    		if ((user = findUser(nick)) != null) {

		    		// Creating new user if the user is returned empty.
					if (user.getNick() == null) {
						twitter4j.User tu = twitter.showUser(nick);
						date = new Timestamp(tu.getCreatedAt().getTime());

						// Creating a new user based on retrieved values from Twitter API.
						createUser(tu.getName(), nick, date, tu.getProfileImageURL());
						cnt ++;
					}
	
				    List<Status> statuses = twitter.getUserTimeline(nick, paging);
				    for (Status s : statuses) {
				    	date = new Timestamp(s.getCreatedAt().getTime());

				    	// Trying to find the user in the database.
				    	if ((tweet = findTweet(nick, date)) != null) {

				    		// Creating new tweet if the tweet is returned empty.
							if (tweet.getTweet() == null) {

								// Creating a new tweet based on retrieved values from Twitter API.
								createTweet(nick, s.getText(), date);
							}
				    	}
				    }
	    		}
		} catch (TwitterException te) {
			te.printStackTrace();
			log.error("Failed to get tweets: " + te.getMessage());
			return cnt;
		}

		log.info(cnt + " new tweets added into the database");
		return cnt;
	}
}
