package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import serialization.Challenge;
import sharedModels.UniversalConstants;
import sharedModels.UserData;

public class MainServer {
	private static ServerSocket socket;
	private static HashMap<String, String> registeredUserData = new HashMap<String, String>(); // stores username and
																								// secretWord
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

	public static boolean registerUser(UserData userData) {
		System.out.println("In registerUser");
		System.out.println(userData.getUsername() + ", " + userData.getSecretWord() + ", " + userData.getPassword());

		registeredUserData.put(userData.getUsername(), userData.getSecretWord());

		updateFile();

		return true;

	}

	public static Challenge generateChallenge(String username) {
		return new Challenge("What was your secret word?", registeredUserData.get(username));
	}

	public static boolean isUsernameAlreadyRegistered(String username) {
		return registeredUserData.containsKey(username);
	}

	private static void createServerSocketAndAssistant() {
		try {
			socket = new ServerSocket(UniversalConstants.SERVER_PORT);
			new AcceptingThread(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<ClientThread> clients = new ArrayList<ClientThread>();

	public static void main(String[] args) {
		MainServer.start();
	}
}