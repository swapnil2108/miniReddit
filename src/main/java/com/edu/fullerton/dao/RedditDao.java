package com.edu.fullerton.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RedditDao {
	Connection con = null;
	protected void createConnection() {		
		try {
			// Registering the HSQLDB JDBC driver
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			// Creating the connection with HSQLDB
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/cpsc476;ifexists=true", "SA", "Passw0rd");
			if (con != null) {
				System.out.println("Connection created successfully");
			} else {
				System.out.println("Problem with creating connection");
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}
	
	public Connection getConnection(){
		return this.con;
	}
	
	protected int hasUsertable(){
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE  IF NOT EXISTS ALLUSERS(USERNAME VARCHAR(50) NOT NULL PRIMARY KEY,PASSWORD VARCHAR(50) NOT NULL);");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int hasPostsTable() {
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS USERSPOST (USERNAME VARCHAR(50) NOT NULL,POSTS VARCHAR(150) NOT NULL, PRIMARY KEY(USERNAME,POSTS));");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public int hasCommentsTable() {
		try {
			PreparedStatement ps = con.prepareStatement("CREATE TABLE IF NOT EXISTS USERSCOMMENTS (POSTS VARCHAR(150) NOT NULL,COMMENTS VARCHAR(100) NOT NULL,COMMENTEDUSER VARCHAR(50),PRIMARY KEY(POSTS,COMMENTS));");
			return ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
