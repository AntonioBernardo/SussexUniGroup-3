package uk.ac.sussex.asegr3.transport.beans;

import java.nio.charset.Charset;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.codec.binary.Base64;

@XmlRootElement
public class TransportAuthenticationToken {
	
	public static final String AUTHENTICATION_SIGNATURE_COOKIE_NAME = "__uk.ac.sussex.asegr3.tracker.AUTH";

	public static final String USERNAME_TAG = "username";
	public static final String SIGNATURE_TAG = "signature";
	public static final String EXPIRES_TAG = "expires";

	@XmlElement(name=USERNAME_TAG, required=true)
	private String username;
	@XmlElement(name=SIGNATURE_TAG, required=true)
	private String signature;
	@XmlElement(name=EXPIRES_TAG, required=true)
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
	
	public String getToken(Base64Encoder encoder){
		String base64EncodedUserName = encoder.encode(getUsername().getBytes(Charset.forName("UTF-8")));
		String base64EncodedSignature = encoder.encode(getSignature().getBytes(Charset.forName("UTF-8")));
		return base64EncodedUserName+"_"+getExpires()+"_"+base64EncodedSignature;
	}
	
	public String toString(){
		return getToken(new Base64Encoder(){

			@Override
			public String encode(byte[] bytes) {
				return Base64.encodeBase64String(bytes);
			}

			@Override
			public byte[] decode(String code) {
				return Base64.decodeBase64(code);
			}
			
		});
	}
	
	public static TransportAuthenticationToken createAuthenticationTokenFromString(String token){
		String[] tokens = token.split("_");
		
		if (tokens.length != 3){
			throw new IllegalArgumentException("Token does not have the correct structure.("+token+")");
		}
		
		String username = new String(Base64.decodeBase64(tokens[0]), Charset.forName("UTF-8"));
		long expires = Long.parseLong(tokens[1]);
		String signature = new String(Base64.decodeBase64(tokens[2]), Charset.forName("UTF-8"));
		
		return new TransportAuthenticationToken(username, signature, expires);
	}

	@Override
	public int hashCode() {
		return this.getUsername().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof TransportAuthenticationToken){
			TransportAuthenticationToken other = (TransportAuthenticationToken)obj;
			
			return this.getUsername().equals(other.getUsername()) &&
					this.getSignature().equals(other.getSignature()) &&
					this.getExpires() == other.getExpires();
		}
		
		return false;
	}
	
	
}
