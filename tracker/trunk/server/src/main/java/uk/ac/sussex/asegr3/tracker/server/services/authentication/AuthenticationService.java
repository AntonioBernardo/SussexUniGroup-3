package uk.ac.sussex.asegr3.tracker.server.services.authentication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException.Type;

public class AuthenticationService {

	private static final byte[] SALT = "aesegr3.pwsalt_r54h2kl".getBytes();
	private static final byte[] SIGNATURE_KEY = "aesegr3.pwsigkey_672kh02k".getBytes();
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final SecretKeySpec SIGNING_KEY = new SecretKeySpec(SIGNATURE_KEY, HMAC_SHA1_ALGORITHM);

	private final UserDao userDao;
	private final long timeToExpire;

	public AuthenticationService(UserDao userDao, long timeToExpire) {
		this.userDao = userDao;
		this.timeToExpire = timeToExpire;
	}

	public AuthenticationToken authenticateUser(String username, String password) {
		
		String pwHash = computePwHash(password);
		
		String storedHash = userDao.getPasswordForUser(username);
		
		if (pwHash.equals(storedHash)){
			long expirationTime = System.currentTimeMillis()+timeToExpire;
			try {
				String signature = calculateSignature(username+""+timeToExpire);
				return new AuthenticationToken(username, expirationTime, signature);
			} catch (SignatureException e) {
				throw new SecurityViolationException(Type.SIGNATURE_GENERATION, e);
			}
			
		} else{
			throw new SecurityViolationException(Type.PASSWORD_MISMATCH);
		}
	}

	private String calculateSignature(String data)
			throws java.security.SignatureException {

		String result;

		try {

			// Get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(SIGNING_KEY);

			// Compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// Convert raw bytes to Hex
			byte[] hexBytes = new Hex().encode(rawHmac);

			// Covert array of Hex bytes to a String
			result = new String(hexBytes, "UTF-8");

		} catch (Exception e) {
			throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
		}
		return result;
	}

	private String computePwHash(String password) {

		MessageDigest digest = DigestUtils.getSha256Digest();
		// Update input string in message digest
		digest.update(password.getBytes(), 0, password.length());
		digest.update(SALT);

		// Converts message digest value in base 16 (hex)
		return new BigInteger(1, digest.digest()).toString(16);
	}

}
