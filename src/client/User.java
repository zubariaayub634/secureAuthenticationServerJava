package client;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.Socket;

import serialization.SerializedObject;
import sharedModels.ReadThread;
import sharedModels.UserData;

/**
 * @author zubxx
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = 4188174434271603974L;
	UserData userData = null;
	private static int serverPort = 4500;

	Socket socket = null;
	ReadThread assistant;

	public User() {
		connectToServer();
	}

	private void connectToServer() {
		try {
			InetAddress host = InetAddress.getLocalHost();
			// ObjectOutputStream oos = null;
			// ObjectInputStream ois = null;
			// establish socket connection to server
			socket = new Socket(host.getHostName(), serverPort);
			// write to socket using ObjectOutputStream
			// oos = new ObjectOutputStream(socket.getOutputStream());
			System.out.println("Sending request to Socket Server");

			// assistant = new ReadThread(this.socket);

			// read the server response message
			// ois = new ObjectInputStream(socket.getInputStream());
			// String message = (String) ois.readObject();
			// System.out.println("Message: " + message);
			// close resources
			// ois.close();
			// oos.close();
			// Thread.sleep(100);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean createSignUpRequest(String username, String email, String password) {
		try {
			this.socket.getOutputStream()
					.write((new SerializedObject<String>()).toByteStream(new String("SignupRequest")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return true;

		/*
		 * userData = new UserData(username, email, password);
		 * 
		 * String temp = new String("SignupRequest"); SerializableMessage lr =
		 * MessageFactory.getMessage(temp); lr.setField("account", userData); UserData t
		 * = (UserData) lr.getField("account");
		 * 
		 * try { this.socket.getOutputStream().write(lr.toBinary()); } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * System.out.println("Message dispatched to Server"); byte[] mType, msg; while
		 * (true) { try { mType = assistant.getOut().remove(); break; } catch
		 * (NoSuchElementException e) { continue; } } System.out.println("m1 received");
		 * while (true) { try { msg = assistant.getOut().remove(); break; } catch
		 * (NoSuchElementException e) { continue; } } System.out.println("m2 received");
		 * String msgType = (new SerializedObject<String>()).fromByteStream(mType);
		 * String receivedMessage = (new
		 * SerializedObject<String>()).fromByteStream(msg); if
		 * (msgType.equals("SignupRequest") &&
		 * receivedMessage.equals("Successful Signup")) { // this.username = ((Account)
		 * lr.getField("account")).getUsername();
		 * System.out.println("Successful Signup"); return true; }
		 * System.out.println("Signup failed"); return false;
		 */
	}

	public boolean createLoginRequest(String username, String password) {
		try {
			this.socket.getOutputStream()
					.write((new SerializedObject<String>()).toByteStream(new String("LoginRequest")));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return true;

		/*
		 * userData = new UserData(username, "", password);
		 * 
		 * String temp = new String("SignupRequest"); SerializableMessage lr =
		 * MessageFactory.getMessage(temp); lr.setField("account", userData); UserData t
		 * = (UserData) lr.getField("account");
		 * 
		 * try { this.socket.getOutputStream().write(lr.toBinary()); } catch
		 * (IOException e1) { // TODO Auto-generated catch block e1.printStackTrace(); }
		 * 
		 * System.out.println("Message dispatched to Server"); byte[] mType, msg; while
		 * (true) { try { mType = assistant.getOut().remove(); break; } catch
		 * (NoSuchElementException e) { continue; } } System.out.println("m1 received");
		 * while (true) { try { msg = assistant.getOut().remove(); break; } catch
		 * (NoSuchElementException e) { continue; } } System.out.println("m2 received");
		 * String msgType = (new SerializedObject<String>()).fromByteStream(mType);
		 * String receivedMessage = (new
		 * SerializedObject<String>()).fromByteStream(msg); if
		 * (msgType.equals("LoginRequest") &&
		 * receivedMessage.equals("Successful Login")) { // this.username = ((Account)
		 * lr.getField("account")).getUsername();
		 * System.out.println("Successful Login"); return true; }
		 * System.out.println("Login failed"); return false;
		 */
	}

	public static void main(String[] args) {
		(new User()).createSignUpRequest("username", "email", "password");
	}
}
