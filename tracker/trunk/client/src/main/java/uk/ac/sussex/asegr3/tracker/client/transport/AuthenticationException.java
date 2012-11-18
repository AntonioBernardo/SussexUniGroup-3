package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;

public class AuthenticationException extends Exception {

	public AuthenticationException(String username, IOException e) {
		super("unable to login as "+username+". Please check your credentials", e);
	}

	public AuthenticationException(String username) {
		this(username, null);
	}

}
