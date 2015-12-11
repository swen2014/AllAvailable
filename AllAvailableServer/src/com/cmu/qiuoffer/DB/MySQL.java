package com.cmu.qiuoffer.DB;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.cmu.qiuoffer.Util.SQLQueryHelper;

/**
 * MySQL class
 * 
 * @author Xingbang (Simba) Tian
 * @verison 1.1
 * @since 11/13/2015
 */
public class MySQL extends CommonDB {
	private Connection conn;
	private SQLQueryHelper helper;

	public MySQL() {
		helper = new SQLQueryHelper();
	}

	private static final String DRIVERNAME = "com.mysql.jdbc.Driver";
	private static final String PROPERTIES_NAME = "connection.properties";
	private static final String URL_KEY = "url";

	/**
	 * Get Access through JDBC
	 * 
	 * @return datbase connection
	 * @throws SQLException
	 *             database connection exception
	 * @throws IOException
	 *             exception of reading properties
	 * @throws ClassNotFoundException
	 *             exception of JDBC driver lost
	 */
	public Connection getConnection() {
		try {
			Class.forName(DRIVERNAME);
			Properties properties = new Properties();
			ClassLoader cl = this.getClass().getClassLoader();
			properties.load(cl.getResourceAsStream(PROPERTIES_NAME));
			return DriverManager.getConnection(properties.getProperty(URL_KEY),
					properties);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException(
					"Cannot find the driver in the classpath!", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Close Database Connection, Statement and Result Set
	 * 
	 * @param rs
	 *            the defined result set
	 * @param st
	 *            the defined statement
	 * @param con
	 *            the defined connection
	 * @throws SQLException
	 *             exception of closing database
	 */
	public void close(ResultSet rs, Statement st, Connection con)
			throws SQLException {

		if (rs != null) {
			rs.close();
		}
		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}
	}

	/**
	 * Close Database Connection, Statement and Result Set
	 * 
	 * @param st
	 *            the defined statement
	 * @param con
	 *            the defined connection
	 * @throws SQLException
	 *             exception of closing database
	 */
	public void close(Statement st, Connection con) throws SQLException {

		if (st != null) {
			st.close();
		}
		if (con != null) {
			con.close();
		}
	}

	/**
	 * Close Database Connection
	 */
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
