package serialization;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializedObject<T extends Serializable> {
	/**
	 * @param object
	 * @return
	 */
	public byte[] toByteStream(T object) {
		byte[] byteArrayObject = null;
		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bos);
			oos.writeObject(object);

			oos.close();
			bos.close();
			byteArrayObject = bos.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			return byteArrayObject;
		}
		return byteArrayObject;
	}

	/**
	 * @param bStream
	 * @return
	 */
	public T fromByteStream(byte[] bStream) {
		ByteArrayInputStream bais;
		ObjectInputStream ins;
		try {

			bais = new ByteArrayInputStream(bStream);

			ins = new ObjectInputStream(bais);
			T i = (T) ins.readObject();

			ins.close();
			return i;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return (T) null;
	}
}
