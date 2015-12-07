package com.cmu.qiuoffer.DAO;

import com.cmu.qiuoffer.Entities.UserBean;

public interface UserDAO {
	/**
	 * Check if user name and password matches
	 * 
	 * @param email
	 * @param password
	 * @return user name (email address)
	 */
	public String loginCheck(String email, String password);

	/**
	 * Check if a user is admin or not
	 * 
	 * @param email
	 * @return
	 */
	public boolean checkType(String email);

	public void createUser(UserBean user);

	public boolean isAvailableAtTime(String email, String date, String time, double duration);
}
