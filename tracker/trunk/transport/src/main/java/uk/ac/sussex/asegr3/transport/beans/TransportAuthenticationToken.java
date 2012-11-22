package uk.ac.sussex.asegr3.transport.beans;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

@XmlRootElement
public class TransportAuthenticationToken {

	private static final String USERNAME_TAG = "username";

	private static final String SIGNATURE_TAG = "sig";

	private static final String EXPIRATION_TAG = "expiry";

	@XmlElement(name=USERNAME_TAG, required=true)
	private String username;
	
	@XmlElement(name=SIGNATURE_TAG, required=true)
	private String signature;
	
	@XmlElement(name=EXPIRATION_TAG, required=true)
	private long expires;
	
	public TransportAuthenticationToken(){
		this("", "", -1);
	}
	
	public TransportAuthenticationToken(String username, String signature, long expires){
		this.setUsername(username);
		this.setSignature(signature);
		this.setExpires(expires);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}
	
	public String getToken(){
		String base64EncodedUserName = Base64.encodeBase64String(getUsername().getBytes(Charset.forName("UTF-8")));
		String base64EncodedSignature = Base64.encodeBase64String(getSignature().getBytes(Charset.forName("UTF-8")));
		return base64EncodedUserName+"_"+getExpires()+"_"+base64EncodedSignature;
	}
	
	public String toString(){
		return getToken();
	}
	
	public static TransportAuthenticationToken createAuthenticationTokenFromString(String token){
		String[] tokens = token.split("_");
		
		if (tokens.length != 3){
			throw new IllegalArgumentException("Token does not have the correct structure.");
		}
		
		String username = new String(Base64.decodeBase64(tokens[0]), Charset.forName("UTF-8"));
		long expires = Long.parseLong(tokens[1]);
		String signature = new String(Base64.decodeBase64(tokens[2]), Charset.forName("UTF-8"));
		
		return new TransportAuthenticationToken(username, signature, expires);
	}
	
}
