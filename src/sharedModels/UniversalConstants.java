package sharedModels;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UniversalConstants {
	public static final int SERVER_PORT = 4500;
	private static final String CRYPTOGRAPHIC_HASH_FUNCTION = "SHA-512";

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
}
