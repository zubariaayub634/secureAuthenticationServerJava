package serialization;

import java.io.Serializable;

public abstract class SerializableMessage implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String msgType;
	protected String senderUsername;
	protected String[] fields;
	// MessageContent msgCont;

	/**
	 * 
	 */
	SerializableMessage() {
		senderUsername = new String();
	}

	/**
	 * @return
	 */
	public String getMsgType() {
		return msgType;
	}

	/**
	 * @param senderUsername
	 */
	public void setSenderUsername(String senderUsername) {
		this.senderUsername = new String(senderUsername);
	}

	/**
	 * @return
	 */
	public String getSenderUsername() {
		return senderUsername;
	}

	/**
	 * @return
	 */
	public abstract byte[] toBinary();

	/**
	 * @param array
	 */
	public abstract void fromBinary(byte[] array);

	/**
	 * @param fieldName
	 * @return
	 */
	public abstract Object getField(String fieldName);

	/**
	 * @param fieldName
	 * @param fieldValue
	 */
	public abstract void setField(String fieldName, Object fieldValue);
}
