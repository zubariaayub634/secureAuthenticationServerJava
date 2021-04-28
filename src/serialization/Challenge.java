package serialization;

import java.io.Serializable;

public class Challenge extends SerializableMessage implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3395802150892798529L;
	private String prompt;
	private String response;

	public Challenge(String p, String r) {
		prompt = p;
		response = r;
	}

	@Override
	public byte[] toBinary() {
		byte[] bArr = (new SerializedObject<Challenge>()).toByteStream(this);
		return bArr;
	}

	@Override
	public void fromBinary(byte[] array) {
		Challenge lr = (new SerializedObject<Challenge>()).fromByteStream(array);
		msgType = lr.msgType;
		senderUsername = lr.senderUsername;
		for (int i = 0; i < fields.length; i++)
			fields[i] = lr.fields[i];
		prompt = lr.prompt;
		response = lr.response;
	}

	@Override
	public Object getField(String fieldName) {
		if (fieldName.compareTo("prompt") == 0) {
			if (prompt == null)
				System.out.println("prompt is still uninitialized");
			return prompt;
		} else if (fieldName.compareTo("response") == 0) {
			if (response == null)
				System.out.println("response is still uninitialized");
			return response;
		}
		return null;
	}

	@Override
	public void setField(String fieldName, Object fieldValue) {
		if (fieldName.compareTo("prompt") == 0) {
			prompt = (String) fieldValue;
		} else if (fieldName.compareTo("response") == 0) {
			response = (String) fieldValue;
		}
	}
}
