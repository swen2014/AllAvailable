package com.cmu.qiuoffer.DB;

import java.sql.ResultSet;

/**
 * Abstract class for database connection
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public abstract class CommonDB {
	public abstract ResultSet executeQuery(String sql);
	public abstract boolean executeUpdate(String sql);
}
