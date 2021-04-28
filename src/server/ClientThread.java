package server;

import java.net.*;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import serialization.Challenge;
import serialization.MessageFactory;
import serialization.SerializableMessage;
import serialization.SerializedObject;
import sharedModels.AES;
import sharedModels.ReadThread;
import sharedModels.UserData;

import java.io.*;

public class ClientThread extends Thread {
	Socket acceptSocket;
	ReadThread assistant;
	// MessageFactory msgFac;
	String username;

	/**
	 * @param os
	 * @throws IOException
	 */
	public ClientThread(Socket os) throws IOException {
		this.acceptSocket = os;
		System.out.println("Comm successful with port " + Integer.toString(this.acceptSocket.getPort()));
		// msgFac = new MessageFactory();
		assistant = new ReadThread(this.acceptSocket);
		// assistant.start();
		start();
	}

	/**
	 * @return
	 */
	public String getUsername() {
		return username;
	}

	/**
	 *
	 */
	public void run() {
		System.out.println("Client Thread run function");
		while (true) {
			try {
				byte[] mType, msg;
				while (true) {
					try {
						// System.out.print(".");
						mType = assistant.getOut().remove();
						System.out.println("Message received");
						break;
					} catch (NoSuchElementException e) {
						continue;
					}
				}
				String msgType = (new SerializedObject<String>()).fromByteStream(mType);
				System.out.println("MsgType: " + msgType);

				/*
				 * if (msgType.equals("GetAllPlaylists")) { System.out.println(msgType); while
				 * (true) { try { msg = assistant.getOut().remove(); break; } catch
				 * (NoSuchElementException e) { continue; } } SerializableMessage
				 * receivedMessage = MessageFactory.getMessage("GetPlaylists");
				 * receivedMessage.fromBinary(msg); JTable table =
				 * MainServer.playlistNameResultSetToJTable( MainServer.getPlaylist((UserData)
				 * receivedMessage.getField("account"))); SerializableMessage getAllSongs =
				 * MessageFactory.getMessage("GetPlaylists");
				 * getAllSongs.setField("allPlaylists", table);
				 * this.acceptSocket.getOutputStream().write(mType);
				 * this.acceptSocket.getOutputStream() .write((new
				 * SerializedObject<GetPlaylists>()).toByteStream((GetPlaylists) getAllSongs));
				 * } else if (msgType.equals("GetAllSongs")) { System.out.println(msgType);
				 * JTable table =
				 * MainServer.songsResultSetToJTable(MainServer.getSongsDetails());
				 * SerializableMessage getAllSongs = MessageFactory.getMessage("GetAllSongs");
				 * getAllSongs.setField("allSongs", table);
				 * this.acceptSocket.getOutputStream().write(mType);
				 * this.acceptSocket.getOutputStream() .write((new
				 * SerializedObject<GetAllSongs>()).toByteStream((GetAllSongs) getAllSongs)); }
				 * else
				 */
				if (msgType.equals("LoginRequest")) {
					System.out.println(msgType);
					while (true) {
						try {
							msg = assistant.getOut().remove();
							break;
						} catch (NoSuchElementException e) {
							continue;
						}
					}
					String username = (new SerializedObject<String>()).fromByteStream(msg);

					Challenge challenge = MainServer.generateChallenge(username);

					// send challenge prompt to client
					this.acceptSocket.getOutputStream().write((new SerializedObject<String>())
							.toByteStream(new String((String) challenge.getField("prompt"))));

					TimeUnit.SECONDS.sleep(1);

					byte[] challengeResponseSerialized;
					while (true) {
						try {
							challengeResponseSerialized = assistant.getOut().remove();
							System.out.print("Challenge response received: ");
							break;
						} catch (NoSuchElementException e) {
							continue;
						}
					}

					String challengeResponse = (new SerializedObject<String>())
							.fromByteStream(challengeResponseSerialized);
					System.out.println(challengeResponse);

					AES aes = new AES(MainServer.getPasswordHash(username));
					String expectedResponse = aes.encrypt((String)challenge.getField("response"));
					System.out.println("expected response: " + expectedResponse);

					boolean res = expectedResponse.equals(challengeResponse);
					// boolean res = true;

					System.out.print("result of login: ");
					System.out.println(res);

					if (res) {
						this.acceptSocket.getOutputStream()
								.write((new SerializedObject<String>()).toByteStream(new String("Successful Login")));
					} else {
						this.acceptSocket.getOutputStream()
								.write((new SerializedObject<String>()).toByteStream(new String("Login failed")));
						MainServer.clients.remove(this);
						break;
					}
				} else
				/*
				 * if (msgType.equals("PlaylistCreationRequest")) { System.out.println(msgType);
				 * while (true) { try { msg = assistant.getOut().remove(); break; } catch
				 * (NoSuchElementException e) { continue; } } SerializableMessage
				 * receivedMessage = MessageFactory.getMessage("PlaylistCreationRequest");
				 * receivedMessage.fromBinary(msg); boolean res =
				 * MainServer.createPlaylist((UserData) receivedMessage.getField("account"),
				 * (String) receivedMessage.getField("playlistName")); if (res) {
				 * this.acceptSocket.getOutputStream().write(mType);
				 * this.acceptSocket.getOutputStream().write((new SerializedObject<String>())
				 * .toByteStream(new String("Successful Playlist Creation"))); } else {
				 * this.acceptSocket.getOutputStream().write(mType);
				 * this.acceptSocket.getOutputStream().write( (new
				 * SerializedObject<String>()).toByteStream(new
				 * String("Failed Playlist Creation"))); MainServer.clients.remove(this); break;
				 * } } else if (msgType.equals("GetSongsOfPlaylist")) {
				 * System.out.println(msgType); while (true) { try { msg =
				 * assistant.getOut().remove(); break; } catch (NoSuchElementException e) {
				 * continue; } } SerializableMessage receivedMessage =
				 * MessageFactory.getMessage("PlaylistCreationRequest");
				 * receivedMessage.fromBinary(msg); JTable table =
				 * MainServer.getSongsOfPlaylist((UserData) receivedMessage.getField("account"),
				 * (String) receivedMessage.getField("playlistName")); SerializableMessage
				 * getAllSongs = MessageFactory.getMessage("GetAllSongs");
				 * getAllSongs.setField("allSongs", table);
				 * this.acceptSocket.getOutputStream().write(mType);
				 * this.acceptSocket.getOutputStream() .write((new
				 * SerializedObject<GetAllSongs>()).toByteStream((GetAllSongs) getAllSongs));
				 * }else
				 */
				if (msgType.equals("SignupRequest")) {
					System.out.println(msgType);
					while (true) {
						try {
							msg = assistant.getOut().remove();
							break;
						} catch (NoSuchElementException e) {
							continue;
						}
					}
					System.out.println("Second msg received");
					SerializableMessage receivedMessage = MessageFactory.getMessage(msgType);
					receivedMessage.fromBinary(msg);
					boolean res = MainServer.isUsernameAlreadyRegistered(
							((UserData) receivedMessage.getField("account")).getUsername());
					if (!res) {
						this.acceptSocket.getOutputStream().write(mType);
						this.acceptSocket.getOutputStream()
								.write((new SerializedObject<String>()).toByteStream(new String("Successful Signup")));
						try {
							MainServer.registerUser((UserData) receivedMessage.getField("account"));
						} catch (Exception e) {
							System.out.println("Exception while adding account to db");
							e.printStackTrace();
						}
					} else {
						this.acceptSocket.getOutputStream().write(mType);
						this.acceptSocket.getOutputStream()
								.write((new SerializedObject<String>()).toByteStream(new String("Signup failed")));
						MainServer.clients.remove(this);
						break;
					}
				}

				/*
				 * else if (msgType.equals("FriendList")) { Message msgToSend =
				 * msgFac.getMessage("FriendList"); msgToSend.setField("friendList", (Object)
				 * baseServer.getFriends(username)); this.acceptSocket.getOutputStream()
				 * .write((new SerializedObject<Message>()).toByteStream(msgToSend)); } else if
				 * (msgType.equals("SendTextMessageToPerson")) { Message msgReceived =
				 * msgFac.getMessage("SendTextMessageToPerson"); while (true) { try { msg =
				 * assistant.getOut().remove(); break; } catch (NoSuchElementException e) {
				 * continue; } } msgReceived.fromBinary(msg); String receiverUsername = (String)
				 * msgReceived.getField("receiverUsername"); ClientThread thread =
				 * baseServer.getClientThread(receiverUsername); if (thread == null) {
				 * System.out.println("User is offline"); return; } // pass msg to readThread of
				 * the receivedThread thread.acceptSocket.getOutputStream().write( (new
				 * SerializedObject<String>()).toByteStream(new
				 * String("ReceiveTextMessageFromPerson")));
				 * thread.acceptSocket.getOutputStream() .write((new
				 * SerializedObject<Message>()).toByteStream(msgReceived)); } else if
				 * (msgType.equals("ReceiveTextMessageFromPerson")) { Message msgReceived =
				 * msgFac.getMessage("ReceiveTextMessageFromPerson"); while (true) { try { msg =
				 * assistant.getOut().remove(); break; } catch (NoSuchElementException e) {
				 * continue; } } msgReceived.fromBinary(msg);
				 * this.acceptSocket.getOutputStream() .write((new
				 * SerializedObject<Message>()).toByteStream(msgReceived)); }
				 */
				/*
				 * else { System.out.println("Invalid Message Type received"); continue; }
				 */
			} catch (Exception e) {
			}
			/*
			 * catch (SocketException e) { // this.baseServer.clients.remove(this);
			 * e.printStackTrace(); break; } catch (IOException e) { e.printStackTrace(); }
			 */
		}

	}

	/*
	 * public void receiveMessage(String senderUsername, SerializableMessage
	 * message) throws IOException { // do nothing, simply pass it to the respective
	 * client object this.acceptSocket.getOutputStream() .write((new
	 * SerializedObject<String>()).toByteStream(new
	 * String("SendTextMessageToPerson"))); message.setField("receiverUsername",
	 * senderUsername); this.acceptSocket.getOutputStream().write((new
	 * SerializedObject<SerializableMessage>()).toByteStream(message)); }
	 */

	/*
	 * public boolean clientLoginFunc(String username, String password) throws
	 * IOException { if (this.validateUserCredentials(username, password) == true) {
	 * this.username = username; return true; } return false;
	 * 
	 * }
	 */

	/*
	 * public boolean validateUserCredentials(String username, String password) {
	 * for (int i = 0; i < this.baseServer.clientAccountsList.size(); i++) { if
	 * (this.baseServer.clientAccountsList.get(i).username.equals(username) &&
	 * this.baseServer.clientAccountsList.get(i).password.equals(password)) { return
	 * true; } } return false; }
	 */
}
