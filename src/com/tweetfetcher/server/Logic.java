package com.tweetfetcher.server;

import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class Logic implements JSONEncodable {

	static Logger log = Logger.getLogger(FetcherServer.class);

	public Logic() {
		
	}

	public String getUsers() {
		List<User> users = DBHelper.listUsers();
		StringBuffer result = new StringBuffer();

		if (!users.isEmpty()) {
			for (User u : users) {
				result.append("#" + u.getId());
				result.append(" " + u.getNick());
				result.append(" (" + u.getName());
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getDate());
				result.append(", " + date + ")\n");
			}
		} else {
			log.warn("No users found in the database.");
		}
		
		return result.toString();
	}
	
	public String getTweets(int id) {
		List<Tweet> tweets = DBHelper.listTweets(Integer.valueOf(id));
		StringBuffer result = new StringBuffer();

		if (!tweets.isEmpty()) {
			result.append("User " + tweets.get(0).getUser().getNick() + " says:\n");
			for (Tweet t : tweets) {
				result.append("#" + t.getId());
				result.append(" - " + t.getTweet());
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getDate());
				result.append("(" + date + ")\n");
			}
		} else {
			log.warn("No tweets found from given user.");
		}
		
		return result.toString();
	}
	
	public String getAll() {
		List<Tweet> tweets = DBHelper.listAll();
		StringBuffer result = new StringBuffer();

		if (!tweets.isEmpty()) {
			for (Tweet t : tweets) {
				result.append("#" + t.getId());
				result.append(" " + t.getUser().getNick());
				result.append(" - " + t.getTweet());
				String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t.getDate());
				result.append(" (" + date + ")\n");
			}
		} else {
			log.warn("No tweets found in the database.");
		}
		
		return result.toString();
	}

	public void updateAll() {
		DBHelper.updateData();
	}

	@Override
	public String jsonEncode() {
		// TODO Auto-generated method stub
		return null;
	}

}
