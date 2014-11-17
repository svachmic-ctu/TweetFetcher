package com.tweetfetcher;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class DBHelper {

	private static String DBURL = "jdbc:mysql://localhost:3306/twitterdb"
								+ "?useUnicode=true&characterEncoding=UTF-8"
								+ "&characterSetResults=UTF-8";
	private static String DBLOGIN = "twitter";
	private static String DBPASSWORD = "twitterpwd";

	static Logger log = Logger.getLogger(TweetFetcher.class);
	
	static List<String> listTweets() {

		List<String> result = new ArrayList<String>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;

		// register JDBC driver
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} 
		
		catch (ClassNotFoundException e) {
			log.error("Error: Unable to find driver for mysql.");
			e.printStackTrace();
			return null;
		}

		try {
			// get a connection to the DB
			conn = DriverManager.getConnection(DBURL, DBLOGIN, DBPASSWORD);
			stmt = conn.createStatement();
			
			// execute query and retrieve results
			rset = stmt.executeQuery("SELECT * FROM tweets");
			// analyze results

			while (rset.next()) {
				StringBuffer sb = new StringBuffer();
				sb.append(rset.getInt("id") + "-");
				sb.append(rset.getString("username") + "-");
				sb.append(rset.getString("tweet"));
				result.add(sb.toString());
				log.trace("Trace: Tweet found: " + sb.toString());
			}
			
		}

		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		finally {
			try { if (rset != null) rset.close(); } catch(Exception e) { e.printStackTrace(); }
			try { if (stmt != null) stmt.close(); } catch(Exception e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch(Exception e) { e.printStackTrace(); }
		}
		
		log.info("Info: Found a total of " + result.size() + " tweets.");
		return result;
	}
}
