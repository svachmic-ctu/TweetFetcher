package com.tweetfetcher.server;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

@Path("/api")
public class TweetFetcher {

	static Logger log = Logger.getLogger(TweetFetcher.class);
	public static final String ACTION = "action";
	public static final String USER_ID = "id";
	public static final String GET_PATH = "/get";
	public static final String POST_PATH = "/post";
	public static final String USER_PATH = "/user";
	public static final String TWEET_PATH = "/tweet";
	public static final String DATA_PATH = "/data";
	public static final String LIST = "list";
	public static final String LIST_ALL = "listall";
	public static final String UPDATE_DATA = "update";
	public static final String ADD_USER = "add";
	public static final String DELETE_USER = "delete";

    public TweetFetcher() {
        log.info("TweetFetcher servlet is running.");
    }

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path(GET_PATH + USER_PATH)
	public List<User> fetchUser(@QueryParam(ACTION) String action) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(LIST)) {
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
	@Path(GET_PATH + TWEET_PATH)
	public List<Tweet> fetchTweet(@QueryParam(ACTION) String action, @QueryParam(USER_ID) String id) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(LIST)) {
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

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(POST_PATH + DATA_PATH)
	public Response updateData(@QueryParam(ACTION) String action) {

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(UPDATE_DATA)) {
			log.info("Updating the database with new tweets.");
			Response result = DBHelper.updateData();
			return result;
		}

		log.error("Unrecongnized command.");
		return null;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path(POST_PATH + USER_PATH)
	public Response addUser(@QueryParam(ACTION) String action, @QueryParam("nick") String nick, @QueryParam("id") int id) {
		
		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals(ADD_USER)) {
			log.info("Adding a new user to the database.");
			Response result = DBHelper.addUser(nick);
			return result;
		}

		else if (action.equals(DELETE_USER)) {
			log.info("Deleting a user from the database.");
			Response result = DBHelper.deleteUser(id);
			return result;
		}

		log.error("Unrecongnized command.");
		return null;
	}
}
