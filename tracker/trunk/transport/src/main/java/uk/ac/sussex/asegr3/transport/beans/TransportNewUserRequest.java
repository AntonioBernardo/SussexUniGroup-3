package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportNewUserRequest {

	public static final String USERNAME_TAG = "username";

	public static final String PASSWORD_TAG = "password";
	
	@XmlElement(name=USERNAME_TAG, required=true)
	private String username;
	
	@XmlElement(name=PASSWORD_TAG, required=true)
	private String password;
	
	public TransportNewUserRequest(String username, String password){
		this();
		setUsername(username);
		setPassword(password);
	}
	public TransportNewUserRequest(){
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
