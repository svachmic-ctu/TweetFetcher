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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */

@XmlRootElement
@Entity
@Table(name = "tweets")
public class Tweet implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "tweet_id")
	private int id;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_id", referencedColumnName="user_id")
	private User user;

	@XmlElement
	@Column(name = "tweet", length = 140, nullable = false)
	private String tweet;

	@XmlElement
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
}
