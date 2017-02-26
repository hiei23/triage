package com.example.triage;

import java.io.Serializable;

/**
 * A class to contain a user's login information.
 */
public class LoginInformation implements Serializable {

    /**
     * Long value allows objects of LoginInformation type to be passed through
     * activities.
     */
    private static final long serialVersionUID = -2455394574062285055L;
    /**
     * This person's username.
     */
    private String username;
    /**
     * This person's account password.
     */
    private String password;
    /**
     * The type of user logging in, either nurse or physician.
     */
    private String type;

    /**
     * Initializes this LoginInformation based on the passed parameters.
     * 
     * @param username
     *            - The person's username.
     * @param password
     *            - The person's passwords.
     * @param type
     *            - The type of user logging in is either a nurse or physician.
     */
    public LoginInformation(String username, String password, String type) {
	this.username = username;
	this.password = password;
	this.type = type;
    }

    /**
     * Returns the person's username.
     * 
     * @return This person's username.
     */
    public String getUsername() {
	return username;
    }

    /**
     * Modifies this person's username.
     * 
     * @param username
     *            - This person't username.
     */
    public void setUsername(String username) {
	this.username = username;
    }

    /**
     * Returns the person's password.
     * 
     * @return This person's password.
     */
    public String getPassword() {
	return password;
    }

    /**
     * Modifies this person's password.
     * 
     * @param password
     *            - This person's password.
     */
    public void setPassword(String password) {
	this.password = password;
    }

    /**
     * Returns the person's type.
     * 
     * @return This person's type.
     */
    public String getType() {
	return type;
    }

    /**
     * Modifies this person's type.
     * 
     * @param type
     *            - This person's type.
     */
    public void setType(String type) {
	this.type = type;
    }
}