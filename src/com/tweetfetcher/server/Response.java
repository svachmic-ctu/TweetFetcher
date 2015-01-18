package com.tweetfetcher.server;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Response implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	private int users = 0;

	@XmlElement
	private int tweets = 0;

	@XmlElement
	private String message = null;

	public int getUsers() {
		return users;
	}

	public int getTweets() {
		return tweets;
	}

	public String getMessage() {
		return message;
	}

	public void setUsers(int users) {
		this.users = users;
	}

	public void setTweets(int tweets) {
		this.tweets = tweets;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
