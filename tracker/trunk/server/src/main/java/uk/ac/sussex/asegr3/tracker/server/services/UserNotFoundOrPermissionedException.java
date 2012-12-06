package uk.ac.sussex.asegr3.tracker.server.services;

public class UserNotFoundOrPermissionedException extends Exception {

	public UserNotFoundOrPermissionedException(String message) {
		super(message);
	}

}
