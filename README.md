TweetFetcher
============

Authors
=======
Jan Neuzil <jneuzil@isep.fr>
Michal Svacha <msvacha@isep.fr>

Eclipse Configuration
=====================

To change context of the servlet go to:
```
Project Properties -> Context root -> / -> Apply
Server -> Clean
```

To extend Tomcat memory in Eclipse go to:
```
Run -> Run Configurations -> Apache Tomcat -> Tomcat Server -> Arguments
```
At the end put:<br>
```
-XX:PermSize=256m -XX:MaxPermSize=512m
```
To enhance OpenJPA right click on jpaenhance.xml:
```
Run As -> Ant Build
```
To connect to twitter via API create and set twitter4j.properties file:
```
$ touch $(PROJECT_DIR)/src/twitter4j.properties
```

Creating databases
==================

Install mysql database:
```
$ sudo apt-get install mysql
```
Connect to the database:
```
$ mysql -u root -p
```

Copy and paste to populate database:
```
DROP DATABASE twitterdb;
CREATE DATABASE twitterdb DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

CONNECT twitterdb;
DROP TABLE IF EXISTS users;
CREATE TABLE users (
	user_id INT NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(15) NOT NULL,
	screen_name VARCHAR(15) NOT NULL,
	joined_date TIMESTAMP,
	PRIMARY KEY(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

DROP TABLE IF EXISTS tweets;
CREATE TABLE tweets (
	tweet_id INT NOT NULL AUTO_INCREMENT,
	user_id INT NOT NULL,
	tweet VARCHAR(140) NOT NULL,
	date TIMESTAMP NOT NULL,
	PRIMARY KEY(tweet_id),
	FOREIGN KEY(user_id) REFERENCES users(user_id)
) ENGINE=InnoDB DEFAULT CHARSET=UTF8;

GRANT ALL on twitterdb.* TO twitter@localhost IDENTIFIED BY 'twitterpwd';
```

Commands
========

http://localhost:8080/api/get/user?action=listusers
http://localhost:8080/api/get/tweet?action=listtweets&userid=1
http://localhost:8080/api/get/tweet?action=listall
http://localhost:8080/api/get/data?action=updatedata

Notes
=====

