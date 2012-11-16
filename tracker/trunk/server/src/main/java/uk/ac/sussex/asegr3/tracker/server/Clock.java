package uk.ac.sussex.asegr3.tracker.server;

/**
 * Clock abstraction so that time can be effectively mocked and controlled
 * @author andrewhaines
 *
 */
public interface Clock {

	public long getCurrentTime();
}
