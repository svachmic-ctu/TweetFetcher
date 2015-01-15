package com.tweetfetcher.server;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private int id;

	@XmlElement
	@Column(name = "user_name", length = 15, nullable = false)
	private String name;

	@XmlElement
	@Column(name = "screen_name", length = 15, nullable = false)
	private String nick;

	@XmlElement
	@Column(name = "joined_date")
	private Timestamp date;

	public User () {
		// Default constructor.
	}

	public User(String name, String nick, Timestamp date) {
		this.name = name;
		this.nick = nick;
		this.date = date;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getNick() {
		return nick;
	}

	public Timestamp getDate() {
		return date;
	}
}
