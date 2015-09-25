package utils;

import org.mindrot.jbcrypt.BCrypt;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
	public static byte[] getSha512(String value) {
		try {
			return MessageDigest.getInstance("SHA-512").digest(value.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Create an encrypted password from a clear string.
	 *
	 * @param clearString the clear string
	 * @return an encrypted password of the clear string
	 * @throws IllegalArgumentException
	 */
	public static String createPassword(String clearString) throws IllegalArgumentException {
		if (clearString == null) {
			throw new IllegalArgumentException("empty.password");
		}

		return BCrypt.hashpw(clearString, BCrypt.gensalt());
	}

	/**
	 * Method to check if entered user password is the same as the one that is
	 * stored (encrypted) in the database.
	 *
	 * @param candidate the clear text
	 * @param encryptedPassword the encrypted password string to check.
	 * @return true if the candidate matches, false otherwise.
	 */
	public static boolean checkPassword(String candidate, String encryptedPassword) {
		if (candidate == null || encryptedPassword == null) {
			return false;
		}
		
		return BCrypt.checkpw(candidate, encryptedPassword);
	}
}
