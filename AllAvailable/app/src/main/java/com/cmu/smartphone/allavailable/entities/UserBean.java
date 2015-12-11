package com.cmu.smartphone.allavailable.entities;

import java.io.Serializable;

/**
 * UserBean Class
 *
 * @author Xingbang (Simba) Tian
 * @version 1.0
 * @since 11/13/2015
 */
public class UserBean implements Serializable {

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
     * Set the email
     *
     * @param email the given email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the password of the user account
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the password of the user account
     *
     * @param password the given password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the name of the user account
     *
     * @return the name of the user account
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of the user account
     *
     * @param name the name of the user account
     */
    public void setName(String name) {
        this.name = name;
    }

}
