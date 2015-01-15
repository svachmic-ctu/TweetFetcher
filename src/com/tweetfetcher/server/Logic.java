package com.tweetfetcher.server;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class Logic {

	static Logger log = Logger.getLogger(FetcherServer.class);

	public Logic() {
		
	}

	public List<User> getUsers() {
		ArrayList<User> result = new ArrayList<User>();
		List<User> users = DBHelper.listUsers();

		if (!users.isEmpty()) {
			for (User u : users) {
				result.add(u);
			}
		} else {
			log.warn("No users found in the database.");
			return null;
		}
		
		return result;
	}
	
	public List<Tweet> getTweets(int id) {
		ArrayList<Tweet> result = new ArrayList<Tweet>();
		List<Tweet> tweets = DBHelper.listTweets(Integer.valueOf(id));

		if (!tweets.isEmpty()) {
			for (Tweet t : tweets) {
				result.add(t);
			}
		} else {
			log.warn("No tweets found from given user.");
		}
		
		return result;
	}
	
	public List<Tweet> getAll() {
		ArrayList<Tweet> result = new ArrayList<Tweet>();
		List<Tweet> tweets = DBHelper.listAll();

		if (!tweets.isEmpty()) {
			for (Tweet t : tweets) {
				result.add(t);
			}
		} else {
			log.warn("No tweets found in the database.");
		}
		
		return result;
	}

	public void updateAll() {
		DBHelper.updateData();
	}
}
