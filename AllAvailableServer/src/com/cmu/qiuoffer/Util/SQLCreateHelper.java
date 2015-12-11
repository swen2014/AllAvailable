/**
 * 
 */
package com.cmu.qiuoffer.Util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.cmu.qiuoffer.DB.MySQL;
import com.cmu.qiuoffer.DB.SQLHelper;

/**
 * Create the database when the server needs to initiate
 * 
 * @author Xi Wang
 * @version 1.0
 */
public class SQLCreateHelper implements SQLHelper {

	private MySQL mySql = new MySQL();

	/**
	 * Create the database
	 */
	public void createDatabase() {
		Connection connection = null;
		Statement statement = null;
		BufferedReader reader = null;
		FileInputStream in = null;
		try {
			connection = mySql.getConnection();
			if (connection != null) {
				System.out.println("Connected to the Mysql Database!");
			} else {
				throw new SQLException();
			}
			statement = connection.createStatement();

			reader = new BufferedReader(new FileReader(new File(
					DATABASEFILEPATH + "createdatabase.sql")));
			String command = null;
			while ((command = reader.readLine()) != null) {
				statement.execute(command);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (in != null) {
					in.close();
				}
				mySql.close(statement, connection);

			} catch (IOException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
