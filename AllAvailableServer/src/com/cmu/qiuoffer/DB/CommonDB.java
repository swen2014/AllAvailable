package com.cmu.qiuoffer.DB;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Abstract class for database connection
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public abstract class CommonDB {
	public abstract Connection getConnection();
	public abstract void close();
}
