package com.tweetfetcher.server;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
@Entity
@Table(name = "tweets")
public class Tweet implements Serializable, JSONEncodable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tweet_id")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	private User user;

	@Column(name = "tweet", length = 140, nullable = false)
	private String tweet;

	@Column(name = "date")
	private Timestamp date;

	public Tweet() {
		// Default constructor.
	}
	
	public Tweet(User user, String tweet, Timestamp date) {	
		this.user = user;
		this.tweet = tweet;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public User getUser() {
		return user;
	}

	public String getTweet() {
		return tweet;
	}

	public Timestamp getDate() {
		return date;
	}

	public JSONObject getJSON() {
		JSONObject json = new JSONObject();
		try {
			json.put("id", id);
			json.put("user", user.getNick());
			json.put("tweet", tweet);
			json.put("date", date);
		}

		catch (JSONException e) {
			return null;
		}

		return json;
	}
}
