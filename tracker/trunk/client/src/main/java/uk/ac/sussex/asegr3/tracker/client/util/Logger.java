package uk.ac.sussex.asegr3.tracker.client.util;

import java.io.Serializable;

public interface Logger extends Serializable{

	void debug(Class<?> className, String message);
	
	void warn(Class<?> className, String message);
	
	void error(Class<?> className, String message);
}
