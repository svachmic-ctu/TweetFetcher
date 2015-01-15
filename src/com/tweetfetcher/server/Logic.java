package com.tweetfetcher.server;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;

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

	public JSONArray getUsers() {
		List<User> users = DBHelper.listUsers();
		JSONArray result = new JSONArray();

		if (!users.isEmpty()) {
			for (User u : users) {
				result.put(u.getJSON());
			}
		} else {
			log.warn("No users found in the database.");
			return null;
		}
		
		return result;
	}
	
	public JSONArray getTweets(int id) {
		List<Tweet> tweets = DBHelper.listTweets(Integer.valueOf(id));
		JSONArray result = new JSONArray();

		if (!tweets.isEmpty()) {
			for (Tweet t : tweets) {
				result.put(t.getJSON());
			}
		} else {
			log.warn("No tweets found from given user.");
		}
		
		return result;
	}
	
	public JSONArray getAll() {
		List<Tweet> tweets = DBHelper.listAll();
		JSONArray result = new JSONArray();

		if (!tweets.isEmpty()) {
			for (Tweet t : tweets) {
				result.put(t.getJSON());
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

/*
 * import java.text.SimpleDateFormat;
 * public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
 * String date = new SimpleDateFormat(TIME_FORMAT).format(t.getDate());
 */