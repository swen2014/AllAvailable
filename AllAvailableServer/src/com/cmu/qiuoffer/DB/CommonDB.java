package com.cmu.qiuoffer.DB;

/**
 * Abstract class for database connection
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public abstract class CommonDB {
	private String url; // Database url
	private String username;
	private String password;
	private Connection conn;
	private Statement stmt;
	
	public abstract ResultSet executeQuery();
	public abstract boolean executeUpdate();
}
