package com.tweetfetcher.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class TweetFetcher.
 *
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */

@Path("/get")
public class FetcherServer {

	static Logger log = Logger.getLogger(FetcherServer.class);
	public static final String LIST_USERS = "listusers";
	public static final String LIST_TWEETS = "listtweets";
	public static final String LIST_ALL = "listall";
	public static final String UPDATE_DATA = "updatedata";

    public FetcherServer() {
        log.info("TweetFetcher servlet is running.");
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user")
	public List<User> fetchUser(@QueryParam("action") String action) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(LIST_USERS)) {
			log.info("Listing all users from the database.");
			List<User> result = DBHelper.listUsers();
			if (!result.isEmpty()) {
				return result;
			} else {
				log.warn("No users found in the database.");
				return null;
			}
		}

		log.error("Unrecongnized command.");
		return null;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/tweet")
	public List<Tweet> fetchTweet(@QueryParam("action") String action, @QueryParam("userid") String id) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(LIST_TWEETS)) {
			if (id == null) {
				log.error("No user ID specified.");
				return null;
			}
			log.info("Listing all tweets from the given user.");
			List<Tweet> result = DBHelper.listTweets(Integer.valueOf(id));
			if (!result.isEmpty()) {
				return result;
			} else {
				log.warn("No users found in the database.");
				return null;
			}
		}

		else if (action.equals(LIST_ALL)) {
			log.info("Listing all tweets from the database.");
			List<Tweet> result = DBHelper.listAll();
			if (!result.isEmpty()) {
				return result;
			} else {
				log.warn("No users found in the database.");
				return null;
			}
		}

		log.error("Unrecongnized command.");
		return null;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/data")
	public String fetchData(@QueryParam("action") String action) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(UPDATE_DATA)) {
			log.info("Updating the database with new tweets.");
			int cnt = DBHelper.updateData();
			String result = "{\"cnt\":" + cnt + "}";
			return result;
		}

		log.error("Unrecongnized command.");
		return null;
	}
}
