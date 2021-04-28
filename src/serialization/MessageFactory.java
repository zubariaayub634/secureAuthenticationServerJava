package serialization;

public class MessageFactory {

	public MessageFactory() {
	}

	/**
	 * @param messageType
	 * @return
	 */
	public static SerializableMessage getMessage(String messageType) {
		/*
		 * if (messageType.equals("LoginRequest")) { return new LoginRequest(); } else
		 * if (messageType.equals("FriendList")) { return new FriendList(); } else if
		 * (messageType.equals("SendTextMessageToPerson")) { return new
		 * SendTextMessageToPerson(); } else if
		 * (messageType.equals("SendImageMessageToPerson")) { return new
		 * SendImageMessageToPerson(); } else
		 */
		if (messageType.equals("SignupRequest")) {
			return new SignupRequest();
		} /*else if (messageType.equals("GetAllSongs")) {
			return new GetAllSongs();
		} else if (messageType.equals("GetPlaylists")) {
			return new GetPlaylists();
		}else if (messageType.equals("PlaylistCreationRequest")) {
			return new PlaylistCreationRequest();
		}*/

		System.out.println("Message Type does not exist");
		return (SerializableMessage) null;
	}
}
