package ChatServer.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class DataDecod {
	
	public DataDecod() {
	}
	
	public  String generateHash(String pass, byte[] salt) throws NoSuchAlgorithmException {
		final String alg = "SHA-256";
		MessageDigest digest = MessageDigest.getInstance(alg);
		digest.reset();
		digest.update(salt);
		byte[] hash = digest.digest(pass.getBytes());
		return bytesToHex(hash);
	}
	
	public  String bytesToHex(byte[] bytes) {
	    StringBuffer result = new StringBuffer();
	    for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
	    return result.toString();
	 }
	
	public byte[] createSalt() {
		byte[] salt = new byte[20];
		SecureRandom random = new SecureRandom();
		random.nextBytes(salt);
		return salt;
	}
}