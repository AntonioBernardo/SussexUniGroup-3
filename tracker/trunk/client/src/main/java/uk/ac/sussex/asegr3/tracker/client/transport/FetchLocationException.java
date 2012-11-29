package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;

public class FetchLocationException extends Exception {
	
	public FetchLocationException(String message) {
		super(message);
	}

	public FetchLocationException(String message, IOException e) {
		super(message, e);
	}

}
