============
TweetFetcher
============

==================
Creating databases
==================

DROP DATABASE twitterdb;
CREATE DATABASE twitterdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CONNECT twitterdb;
DROP TABLE IF EXISTS tweets;
CREATE TABLE tweets (
	id int(11) NOT NULL auto_increment,
	username varchar(15) NOT NULL,
	tweet varchar(140) NOT NULL,
	PRIMARY KEY(id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

INSERT INTO tweets(username, tweet) VALUES("JohnnyCzech", "Francouzi jsou ignorantsky hovada.");
INSERT INTO tweets(username, tweet) VALUES("MrSvacha", "Dostal jsem praci v Googlu.");

GRANT ALL on twitterdb.* TO twitter@localhost IDENTIFIED BY 'twitterpwd';

=====
Notes
=====

Jeste neni zadani, ale co si pamatuju tak by mely byt 2 databaze ne?

