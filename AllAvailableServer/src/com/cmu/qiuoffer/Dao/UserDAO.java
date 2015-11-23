package com.cmu.qiuoffer.Dao;

public interface UserDAO {
		/**
		 * Check if user name and password matches
		 * @param email
		 * @param password
		 * @return
		 */
		public boolean loginCheck(String email, String password);
		
		/**
		 * Check if a user is admin or not
		 * @param email
		 * @return
		 */
		public boolean checkType(String email);
}
