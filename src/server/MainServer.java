package server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import sharedModels.UniversalConstants;
import sharedModels.UserData;

public class MainServer {
	private static AcceptingThread assistant;
	private static ServerSocket socket;
	private static HashMap<String, String> registeredUserData = new HashMap<String, String>(); // stores username and
																								// password
	private static final String CRYPTOGRAPHIC_HASH_FUNCTION = "SHA-512";
	private static final String USER_DATA_FILE_NAME = "userData.csv";

	public static void start() {
		createServerSocketAndAssistant();

		// populate hashmap from file
		try {
			BufferedReader csvReader = new BufferedReader(new FileReader(USER_DATA_FILE_NAME));

			String row;
			while ((row = csvReader.readLine()) != null) {
				String[] data = row.split(",");
				registeredUserData.put(data[0], data[1]);
			}
			csvReader.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	public static void updateFile() {
		try {
			FileWriter csvWriter = new FileWriter(USER_DATA_FILE_NAME);
			for (Map.Entry<String, String> entry : registeredUserData.entrySet()) {
				String key = entry.getKey();
				String value = entry.getValue();
				csvWriter.append(key + "," + value + "\n");
			}
			csvWriter.close();
		} catch (IOException e) {
			// e.printStackTrace();
		}
	}

	/*
	 * public static boolean deleteUsersRow(String username) { return
	 * mySQLHandler.executeUpdate(DBConnection,
	 * "DELETE FROM user WHERE user.username='" + username + "';"); }
	 * 
	 * public static boolean verifyUserCredentials(UserData userData) { ResultSet r
	 * = mySQLHandler.runSelectQuery("select * from user where user.username='" +
	 * userData.getUsername() + "' OR user.email='" + userData.getUsername() +
	 * "';"); try { if (r.next()) { mySQLHandler.executeUpdate(DBConnection,
	 * "update user " + " set user.lastAccessDate=now() " + "where user.username='"
	 * + userData.getUsername() + "' OR user.email='" + userData.getUsername() +
	 * "';"); return true; } } catch (SQLException e) { // TODO Auto-generated catch
	 * block e.printStackTrace(); } return false; }
	 * 
	 * public static JTable getAllUsersAndDates() { ResultSet songs = mySQLHandler
	 * .runSelectQuery("select user.username, user.dateOfRegistration, user.lastAccessDate from user;"
	 * ); Vector<String> columns = new Vector<String>(Arrays.asList("Username",
	 * "DateOfRegistration", "LastAccessDate")); Vector<Vector<String>> data = new
	 * Vector<Vector<String>>(); for (int i = 0; i < 10; i++) { Vector<String> row =
	 * new Vector<String>(); try { if (songs.next()) { for (int j = 1; j <=
	 * columns.size(); j++) { row.add(songs.getString(j)); } } else break; } catch
	 * (SQLException e1) { // TODO Auto-generated catch block e1.printStackTrace();
	 * } data.add(row); } final JTable songsList = new JTable(data, columns);
	 * resizeColumnWidth(songsList); return songsList; }
	 */
	public static boolean registerUser(UserData userData) {
		System.out.println("In registerUser");
		System.out.println(userData.getUsername() + ", " + userData.getEmail() + ", " + userData.getPassword());

		registeredUserData.put(userData.getUsername(), encryptString(userData.getPassword()));

		updateFile();

		return true;

	}

	public static boolean isUsernameAlreadyRegistered(String username) {
		/*
		 * ResultSet r =
		 * mySQLHandler.runSelectQuery("select * from user where user.username='" +
		 * username + "';"); try { if (r.next()) { return true; } } catch (SQLException
		 * e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 */

		// TODO: implement file handling here

		return registeredUserData.containsKey(username);
	}

	public static String encryptString(String input) {
		try {
			byte[] messageDigest = MessageDigest.getInstance(CRYPTOGRAPHIC_HASH_FUNCTION).digest(input.getBytes());
			String hashtext = (new BigInteger(1, messageDigest)).toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		}

		// For specifying wrong message digest algorithms
		catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static void createServerSocketAndAssistant() {
		try {
			socket = new ServerSocket(UniversalConstants.SERVER_PORT);
			assistant = new AcceptingThread(socket);
		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	public static void main(String[] args) {
		MainServer.start();
	}
}