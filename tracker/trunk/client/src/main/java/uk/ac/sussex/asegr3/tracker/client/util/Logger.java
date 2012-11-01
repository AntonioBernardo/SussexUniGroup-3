package uk.ac.sussex.asegr3.tracker.client.util;

public interface Logger {

	void debug(Class<?> className, String message);
	
	void warn(Class<?> className, String message);
	
	void error(Class<?> className, String message);
}
