package com.cmu.qiuoffer.DB;

public abstract class CommonDB {
	private String url;
	private String username;
	private String password;
	private Connection conn;
	private Statement stmt;
	
	public abstract ResultSet executeQuery();
	public abstract boolean executeUpdate();
}
