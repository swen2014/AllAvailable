package com.cmu.qiuoffer.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cmu.qiuoffer.Util.SQLQueryHelper;

/**
 * MySQL class
 * @author Xingbang (Simba) Tian
 * @verison 1.1
 * @since 11/13/2015
 */
public class MySQL extends CommonDB{
	private String url; // Database url
	private String username;
	private String password;
	private Connection conn;
	private Statement stmt;
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
		    throw new IllegalStateException("Cannot find the driver in the classpath!", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getStatement() {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ResultSet executeQuery(String sql) {
		// TODO Auto-generated method stub
		try {
			connect();
			getStatement();
			return stmt.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean executeUpdate(String sql) {
		// TODO Auto-generated method stub
		return false;
	}	
}
