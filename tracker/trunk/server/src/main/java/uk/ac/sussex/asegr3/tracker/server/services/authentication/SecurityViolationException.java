package uk.ac.sussex.asegr3.tracker.server.services.authentication;

public class SecurityViolationException extends RuntimeException {

	public static enum Type {
		PASSWORD_MISMATCH, 
		SIGNATURE_GENERATION
	}
	public SecurityViolationException(Type type){
		super(type.toString());
	}
	public SecurityViolationException(Type type, Throwable cause) {
		super(type.toString(), cause);
	}
}
