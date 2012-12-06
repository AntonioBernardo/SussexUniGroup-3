package uk.ac.sussex.asegr3.tracker.server.services.authentication;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SignatureException;
import java.util.concurrent.TimeUnit;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import uk.ac.sussex.asegr3.tracker.server.Clock;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException.Type;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

public class AuthenticationService {

	private static final byte[] SALT = "aesegr3.pwsalt_r54h2kl".getBytes();
	private static final byte[] SIGNATURE_KEY = "aesegr3.pwsigkey_672kh02k".getBytes();
	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
	private static final SecretKeySpec SIGNING_KEY = new SecretKeySpec(SIGNATURE_KEY, HMAC_SHA1_ALGORITHM);

	private final UserDao userDao;
	private final long timeToExpire;
	private final Clock currentClock;

	public AuthenticationService(UserDao userDao, int timeToExpire, Clock currentClock) {
		this.userDao = userDao;
		this.timeToExpire = TimeUnit.SECONDS.toMillis(timeToExpire);
		this.currentClock = currentClock;
	}

	public AuthenticationToken authenticateUser(String username, String password) throws SecurityViolationException {
		
		String pwHash = computePwHash(password);
		
		String storedHash = userDao.getPasswordForUser(username);
		
		if (storedHash == null){
			throw new SecurityViolationException(Type.USER_NOT_FOUND);
		}
		
		if (pwHash.equals(storedHash)){
			long expirationTime = currentClock.getCurrentTime()+timeToExpire;
			try {
				String signature = calculateSignature(buildCompositeDataForSignature(username, expirationTime));
				userDao.updateLastLoggin(username, System.currentTimeMillis());
				return new AuthenticationToken(username, expirationTime, signature);
			} catch (SignatureException e) {
				throw new SecurityViolationException(Type.SIGNATURE_GENERATION, e);
			}
			
		} else{
			throw new SecurityViolationException(Type.PASSWORD_MISMATCH);
		}
	}
	
	private String buildCompositeDataForSignature(String username, long expirationTime){
		return username+""+expirationTime;
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

	public String computePwHash(String password) {

		MessageDigest digest = DigestUtils.getSha256Digest();
		// Update input string in message digest
		digest.update(password.getBytes(), 0, password.length());
		digest.update(SALT);

		// Converts message digest value in base 16 (hex)
		return new BigInteger(1, digest.digest()).toString(16);
	}

	public boolean validateToken(TransportAuthenticationToken credentials) throws SignatureException {
		String expectedSignature = calculateSignature(buildCompositeDataForSignature(credentials.getUsername(), credentials.getExpires()));
		
		if (expectedSignature.equals(credentials.getSignature())){
			if (credentials.getExpires() > currentClock.getCurrentTime()){
				return true;
			}
		}
		
		return false;
	}

}
