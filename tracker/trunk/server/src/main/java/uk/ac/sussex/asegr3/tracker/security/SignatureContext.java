package uk.ac.sussex.asegr3.tracker.security;

public interface SignatureContext {

	public String getSignature();
	
	public long getExpirationDate();
	
	public String getUsername();
	
}
