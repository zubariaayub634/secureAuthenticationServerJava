package sharedModels;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String secretWord;
	private String password;
	private Date dateOfRegistration = null;
	private Date lastAccessDate = null;

	/**
	 * @param username
	 * @param email
	 * @param password
	 */
	public UserData(String username, String secretWord, String password) {
		super();
		this.username = username;
		this.secretWord = secretWord;
		this.password = password;
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return
	 */
	public String getSecretWord() {
		return secretWord;
	}

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return
	 */
	public Date getDateOfRegistration() {
		return dateOfRegistration;
	}

	/**
	 * @return
	 */
	public Date getLastAccessDate() {
		return lastAccessDate;
	}

}
