package com.cmu.qiuoffer.Util;

import java.io.*;
import java.util.Properties;

public class SQLQueryHelper {

	private static final String DATABASEFILEPATH = "DBFile/";
	
	public String getSQLTemplate(String key) {
		
		// retrieve prepare query list
		Properties querylist = new Properties();

		try {
			FileInputStream in = new FileInputStream(DATABASEFILEPATH + "query.properties");
			querylist.load(in);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return querylist.getProperty(key);		
	}
}
