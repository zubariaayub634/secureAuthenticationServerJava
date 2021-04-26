package sharedModels;

import java.io.Serializable;
import java.util.Date;

public class UserData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
	private String email;
	private String password;
	private Date dateOfRegistration = null;
	private Date lastAccessDate = null;

	/**
	 * @param username
	 * @param email
	 * @param password
	 */
	public UserData(String username, String email, String password) {
		super();
		this.username = username;
		this.email = email;
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
	public String getEmail() {
		return email;
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
