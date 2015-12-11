package com.cmu.qiuoffer.DB;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract class for database connection
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public abstract class CommonDB {
	/**
	 * Get the connection to the DB
	 * 
	 * @return the connection object
	 */
	public abstract Connection getConnection();

	/**
	 * Close the connection
	 */
	public abstract void close();
}
