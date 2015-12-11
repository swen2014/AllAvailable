package com.cmu.qiuoffer.DAO;

import com.cmu.qiuoffer.Entities.UserBean;

/**
 * Define the User operations
 * 
 * @author Dudaxi Huang
 * @version 1.0
 */
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

	/**
	 * Create a new user
	 * 
	 * @param user
	 */
	public void createUser(UserBean user);

	/**
	 * Check the time is available for the user
	 * 
	 * @param email
	 * @param date
	 * @param time
	 * @param duration
	 * @return whether the time is available for the user
	 */
	public boolean isAvailableAtTime(String email, String date, String time,
			double duration);
}
