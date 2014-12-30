package com.tweetfetcher.server;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class Logic {

	public static final String ACTION = "action";
	public static final String USER_ID = "userid";
	public static final String LIST_USERS = "listusers";
	public static final String LIST_TWEETS = "listtweets";
	public static final String LIST_ALL = "listall";
	public static final String UPDATE_DATA = "updatedata";

	static Logger log = Logger.getLogger(FetcherServer.class);
	private HttpServletRequest request;
	
	public Logic(HttpServletRequest request) {
		this.request = request;
	};

	public String parseParameters() {
		String action = request.getParameter(ACTION);
		String id = request.getParameter(USER_ID);

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(LIST_USERS)) {
			log.info("Listing all users from the database.");
			return getUsers(DBHelper.listUsers());
		}

		else if (action.equals(LIST_TWEETS)) {
			if (id == null) {
				log.error("No user ID specified.");
				return null;
			}
			log.info("Listing all tweets from the given user.");
			return getTweets(DBHelper.listTweets(Integer.valueOf(id)));
		}

		else if (action.equals(LIST_ALL)) {
			log.info("Listing all tweets from the database.");
			return getAll(DBHelper.listAll());
		}

		else if (action.equals(UPDATE_DATA)) {
			log.info("Updating the database with new tweets.");
			DBHelper.updateData();
			return null;
		}

		return null;
	}
	
	public String getUsers(List<User> users) {
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
	
	public String getTweets(List<Tweet> tweets) {
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
	
	public String getAll(List<Tweet> tweets) {
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
}
