package com.cmu.qiuoffer.Util;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.cmu.qiuoffer.DB.SQLHelper;

/**
 * The tool helps querying the database
 * 
 * @author Xi Wang
 * @version 1.0
 */
public class SQLQueryHelper implements SQLHelper {

	/**
	 * Get the SQL template string
	 * 
	 * @param key
	 * @return the template string
	 */
	public String getSQLTemplate(String key) {

		// retrieve prepare query list
		Properties querylist = new Properties();
		InputStream in = null;
		String result = null;
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			in = cl.getResourceAsStream(DATABASEFILEPATH + "query.properties");
			querylist.load(in);
			result = querylist.getProperty(key);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return result;
	}
}
