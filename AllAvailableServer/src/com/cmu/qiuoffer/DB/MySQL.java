package com.cmu.qiuoffer.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import com.cmu.qiuoffer.Util.SQLQueryHelper;

/**
 * MySQL class
 * 
 * @author Xingbang (Simba) Tian
 * @verison 1.1
 * @since 11/13/2015
 */
public class MySQL extends CommonDB {
	private String url; // Database url
	private String username;
	private String password;
	private Connection conn;
	private SQLQueryHelper helper;

	public MySQL() {
		helper = new SQLQueryHelper();
	}

	private void connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
			url = helper.getSQLTemplate("url");
			username = helper.getSQLTemplate("username");
			password = helper.getSQLTemplate("password");
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Database connected!");
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"Cannot find the driver in the classpath!", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Connection getConnection() {
		this.connect();
		return conn;
	}

	
	public void close() {
		try {
			if (conn != null)
				conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
