package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;

public class NewUserSignupException extends Exception {

	public NewUserSignupException(String message) {
		super(message);
	}

	public NewUserSignupException(String message, IOException e) {
		super(message, e);
	}

}
