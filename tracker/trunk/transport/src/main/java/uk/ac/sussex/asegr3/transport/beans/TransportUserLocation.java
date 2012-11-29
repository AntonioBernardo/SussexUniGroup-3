package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;

public class TransportUserLocation {
	
	public static final String USERNAME_TAG = "username";
	public static final String LOCATION_TAG = "location";
	
	@XmlElement(name=USERNAME_TAG, required=true)
	private String username;
	
	@XmlElement(name=LOCATION_TAG, required=true)
	private TransportLocation location;
	
	public TransportUserLocation(String username, TransportLocation location){
		this.setLocation(location);
		this.setUsername(username);
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public TransportLocation getLocation() {
		return location;
	}
	
	public void setLocation(TransportLocation location) {
		this.location = location;
	}
}
