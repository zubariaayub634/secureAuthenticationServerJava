package client;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;
import java.util.NoSuchElementException;

import serialization.MessageFactory;
import serialization.SerializableMessage;
import serialization.SerializedObject;
import sharedModels.ReadThread;
import sharedModels.UniversalConstants;
import sharedModels.UserData;

public class User implements Serializable {

	private static final long serialVersionUID = 4188174434271603974L;
	UserData userData = null;

	Socket socket = null;
	ReadThread assistant;

	public User() {
		connectToServer();
	}

	private void connectToServer() {
		try {
			InetAddress host = InetAddress.getLocalHost();
			socket = new Socket(host.getHostName(), UniversalConstants.SERVER_PORT);
			System.out.println("Sending request to Socket Server");

			assistant = new ReadThread(this.socket);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createSignUpRequest(String username, String secretWord, String password) {

		String messageType = new String("SignupRequest");

		try {
			this.socket.getOutputStream().write((new SerializedObject<String>()).toByteStream(new String(messageType)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		userData = new UserData(username, secretWord, "");// password is not sent to server

		SerializableMessage signupRequestMessage = MessageFactory.getMessage(messageType);
		signupRequestMessage.setField("account", userData);

		try {
			this.socket.getOutputStream().write(signupRequestMessage.toBinary());
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Message dispatched to Server");
		byte[] mType, msg;
		while (true) {
			try {
				mType = assistant.getOut().remove();
				break;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		System.out.println("m1 received");
		while (true) {
			try {
				msg = assistant.getOut().remove();
				break;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		System.out.println("m2 received");
		String msgType = (new SerializedObject<String>()).fromByteStream(mType);
		String receivedMessage = (new SerializedObject<String>()).fromByteStream(msg);
		if (msgType.equals(messageType) && receivedMessage.equals("Successful Signup")) {
			// this.username = ((Account)lr.getField("account")).getUsername();
			System.out.println("Successful Signup");
			return true;
		}
		System.out.println("Signup failed");
		return false;

	}

	public boolean createLoginRequest(String username, String secureWord, String password) {
		String msgType = "LoginRequest";
		try {
			this.socket.getOutputStream().write((new SerializedObject<String>()).toByteStream(new String(msgType)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		String passwordHash=UniversalConstants.encryptString(password);

		try {
			this.socket.getOutputStream().write((new SerializedObject<String>()).toByteStream(new String(username)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("Message dispatched to Server");

		byte[] msg;
		while (true) {
			try {
				msg = assistant.getOut().remove();
				break;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		System.out.println("challenge prompt received");
		String challengePrompt = (new SerializedObject<String>()).fromByteStream(msg);
		System.out.println("challengePrompt " + challengePrompt);
		// todo use this received obj to prompt user for response later

		// respond to challenge - for now simply returns the secureWord
		try {
			this.socket.getOutputStream().write((new SerializedObject<String>()).toByteStream(secureWord));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		while (true) {
			try {
				msg = assistant.getOut().remove();
				break;
			} catch (NoSuchElementException e) {
				continue;
			}
		}
		System.out.println("login status received");
		String receivedMessage = (new SerializedObject<String>()).fromByteStream(msg);
		System.out.println(receivedMessage);

		if (msgType.equals("LoginRequest") && receivedMessage.equals("Successful Login")) {
			System.out.println("Successful Login");
			return true;
		}
		System.out.println("Login failed");
		return false;

	}

	public static void main(String[] args) {
		(new User()).createLoginRequest("username", "secretWord", "password");
	}
}
