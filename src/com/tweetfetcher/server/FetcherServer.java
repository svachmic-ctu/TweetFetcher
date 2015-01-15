package com.tweetfetcher.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;

/**
 * Servlet implementation class TweetFetcher.
 *
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
public class FetcherServer extends HttpServlet {

	static Logger log = Logger.getLogger(FetcherServer.class);
	private static final long serialVersionUID = 1L;
	public static final String ACTION = "action";
	public static final String USER_ID = "userid";
	public static final String LIST_USERS = "listusers";
	public static final String LIST_TWEETS = "listtweets";
	public static final String LIST_ALL = "listall";
	public static final String UPDATE_DATA = "updatedata";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public FetcherServer() {
        super();
        log.info("TweetFetcher servlet is running.");
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter(ACTION);
		String id = request.getParameter(USER_ID);
		PrintWriter pw = response.getWriter();
		Logic logic = new Logic();

		if (action == null) {
			log.error("No action specified.");
			pw.println("Error: No action specified.");
		}

		else if (action.equals(LIST_USERS)) {
			log.info("Listing all users from the database.");
			JSONArray result = logic.getUsers();
			pw.println(result.toString());
		}

		else if (action.equals(LIST_TWEETS)) {
			if (id == null) {
				log.error("No user ID specified.");
				pw.println("Error: No user ID specified.");
			}
			log.info("Listing all tweets from the given user.");
			JSONArray result = logic.getTweets(Integer.valueOf(id));
			pw.println(result.toString());
		}

		else if (action.equals(LIST_ALL)) {
			log.info("Listing all tweets from the database.");
			JSONArray result = logic.getAll();
			pw.println(result.toString());
		}

		else if (action.equals(UPDATE_DATA)) {
			log.info("Updating the database with new tweets.");
			logic.updateAll();
			pw.println("Info: Updating the database with new tweets.");
		}
		
		else {
			log.error("Unrecongnized command.");
			pw.println("Error: Unrecongnized command.");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
