package com.cmu.qiuoffer.Entities;

/**
 * UserBean Class
 * 
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class UserBean {

	private String email;
	private String password;
	private String name;

	/**
	 * Get the email of the user
	 * 
	 * @return the email of the user
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * Set the email of the user
	 * 
	 * @param email
	 *            the given email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Get the password of the user
	 * 
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Set the password of the user
	 * 
	 * @param password
	 *            the given password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get the name of the user
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the user
	 * 
	 * @param name
	 *            the user name
	 */
	public void setName(String name) {
		this.name = name;
	}

}
