package com.tweetfetcher;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

public class Logic {

	static Logger log = Logger.getLogger(TweetFetcher.class);
	private HttpServletRequest request;
	
	public Logic(HttpServletRequest src) {
		request = src;
	};
	
	public String listDatabase() {
		String action = request.getParameter("action");
		
		List<String> result = new ArrayList<String>();

		if (action == null) {
			log.error("No action specified.");
			return null;
		}

		else if (action.equals("list")) {
			log.info("List database request received.");
			result = DBHelper.listTweets();
		}

		return result.toString();
	}

}