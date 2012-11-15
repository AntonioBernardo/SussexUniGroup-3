package uk.ac.sussex.asegr3.tracker.server.services.authentication;

public class AuthenticationToken {

	private final String username;
	private final long expires;
	private final String signature;
	
	public AuthenticationToken(String username, long expires, String signature){
		this.username = username;
		this.expires = expires;
		this.signature = signature;
	}

	public String getUsername() {
		return username;
	}

	public long getExpires() {
		return expires;
	}

	public String getSignature() {
		return signature;
	}
}
