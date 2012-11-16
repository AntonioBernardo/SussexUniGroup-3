package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportAuthenticationRequest {

	public static final String PASSWORD_TAG = "password";
	
	@XmlElement(name=PASSWORD_TAG, required=true)
	private String password;
	
	public TransportAuthenticationRequest(String password){
		this.password = password;
	}
	
	public TransportAuthenticationRequest(){
		this("");
	}
	
	public String getPassword(){
		return password;
	}
}