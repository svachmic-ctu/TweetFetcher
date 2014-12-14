package com.tweetfetcher;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author      Jan Neuzil     <jneuzil@isep.fr>
 * @author      Michal Svacha  <msvacha@isep.fr>
 * @version     0.1
 * @since       2014-11-17
 */
@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name = "user_id")
	private int id;

	@Column(name = "username", length = 15, nullable = false)
	private String username;

	@Column(name = "nickname", length = 15, nullable = false)
	private String nickname;

	@Column(name = "joined_date")
	private Timestamp date;

	public User () {
		// Default constructor.
	}

	public User(String username, String nickname, Timestamp joinedDate) {
		this.username = username;
		this.nickname = nickname;
		this.date = joinedDate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return username;
	}

	public String getNick() {
		return nickname;
	}

	public Timestamp getDate() {
		return date;
	}
}
