package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;

public class AuthenticationException extends Exception {

	public AuthenticationException(String username, IOException e, int statusCode) {
		super("unable to login as "+username+". Please check your credentials"+((statusCode != 0)?".Server returned "+statusCode:""), e);
	}
	
	public AuthenticationException(String username, IOException e) {
		this(username, e, 0);
	}

	public AuthenticationException(String username, int statusCode) {
		this(username, null, statusCode);
	}

}
