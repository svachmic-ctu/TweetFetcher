TweetFetcher
============
A simple web service to provide last tweets from selected 
Twitter users. 

Authors
=======
Jan Neuzil <jneuzil@isep.fr><br>
Michal Svacha <msvacha@isep.fr>

Functionality
=============


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
At the end put:
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

MySQL Configuration
===================

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
	picture VARCHAR(512),
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

AngularJS Configuration
=======================

Install Ruby version management:
```
$ sudo apt-get install rbenv
```
Install Sass:
```
$ sudo gem install sass
```
Go to WebContent:
```
$ cd WebContent
```
Create build directories:
```
$ mkdir build
$ mkdir build/css
```
Run Sass and delete useless cache:
```
$ sass scss/gdg.scss build/css/gdg.css
$ rm -r .sass-cache
```

Commands
========

To test the server side:
```
$ wget http://localhost:8080/var/api/get/user?action=list
$ wget http://localhost:8080/var/api/get/tweet?action=list&id=1
$ wget http://localhost:8080/var/api/get/tweet?action=listall
$ wget http://localhost:8080/var/api/post/data?action=update
$ wget http://localhost:8080/var/api/post/user?action=add&nick=altolabs
```

Notes
=====

