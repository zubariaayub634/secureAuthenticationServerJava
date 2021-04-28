package serialization;

import java.io.Serializable;

import sharedModels.UserData;

public class SignupRequest extends SerializableMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5328115428979083095L;
	private UserData account;

	/**
	 * 
	 */
	public SignupRequest() {
		msgType = new String("SignupRequest");
		fields = new String[5];
		account = null;
	}

	/**
	 *
	 */
	@Override
	public byte[] toBinary() {
		byte[] bArr = (new SerializedObject<SignupRequest>()).toByteStream(this);
		return bArr;
	}

	/**
	 *
	 */
	@Override
	public void fromBinary(byte[] array) {
		// TODO Auto-generated method stub
		SignupRequest lr = (new SerializedObject<SignupRequest>()).fromByteStream(array);
		msgType = lr.msgType;
		senderUsername = lr.senderUsername;
		for (int i = 0; i < fields.length; i++)
			fields[i] = lr.fields[i];
		account = lr.account;
	}

	/**
	 *
	 */
	@Override
	public Object getField(String fieldName) {
		if (fieldName.compareTo("account") == 0) {
			if (account == null)
				System.out.println("Account is still uninitialized");
			return account;
		}
		return null;
	}

	/**
	 *
	 */
	@Override
	public void setField(String fieldName, Object fieldValue) {
		if (fieldName.equals("account")) {
			account = (UserData) fieldValue;
			System.out.println("Feild account has been set");
		}
	}
}
