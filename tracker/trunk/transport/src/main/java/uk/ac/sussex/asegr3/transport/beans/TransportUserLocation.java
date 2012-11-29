package uk.ac.sussex.asegr3.transport.beans;

import java.util.Collection;
import java.util.Collections;

import javax.xml.bind.annotation.XmlElement;

public class TransportUserLocation {
	

	public static final String USERNAME_TAG = "username";
	public static final String LOCATION_TAG = "location";
	public static final String COMMENTS_TAG = "comments";

	
	@XmlElement(name=USERNAME_TAG, required=true)
	private String username;
	
	@XmlElement(name=LOCATION_TAG, required=true)
	private TransportLocation location;
	
	@XmlElement(name=COMMENTS_TAG, required=false)
	private Collection<TransportComment> comments;
	
	public TransportUserLocation(){
		this("", null, Collections.<TransportComment>emptyList());
	}
	
	public TransportUserLocation(String username, TransportLocation location, Collection<TransportComment> comments){
		this.setLocation(location);
		this.setUsername(username);
		this.setComments(comments);
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

	public Collection<TransportComment> getComments() {
		return comments;
	}

	public void setComments(Collection<TransportComment> comments) {
		this.comments = comments;
	}
}
