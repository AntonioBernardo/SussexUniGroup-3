package uk.ac.sussex.asegr3.tracker.server.services.authentication;

public class SecurityViolationException extends Exception {

	public static enum Type {
		PASSWORD_MISMATCH, 
		SIGNATURE_GENERATION, 
		USER_NOT_FOUND, 
		INVALID_SIGNATURE
	}
	public SecurityViolationException(Type type){
		super(type.toString());
	}
	public SecurityViolationException(Type type, Throwable cause) {
		super(type.toString(), cause);
	}
}
