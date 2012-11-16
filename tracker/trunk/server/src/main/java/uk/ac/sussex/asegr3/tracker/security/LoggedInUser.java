package uk.ac.sussex.asegr3.tracker.security;

/**
 * Defines authenitcation details for a logged in user
 * @author andrewhaines
 *
 */
public class LoggedInUser {

	private final long sessionExpires;
	private final String username;
	
	public LoggedInUser(long sessionExpires, String username){
		this.sessionExpires = sessionExpires;
		this.username = username;
	}
	
	public String getUsername(){
		return username;
	}
	
	public long getSessionExpires(){
		return sessionExpires;
	}
}
