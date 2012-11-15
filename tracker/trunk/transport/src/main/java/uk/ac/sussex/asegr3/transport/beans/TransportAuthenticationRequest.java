package uk.ac.sussex.asegr3.transport.beans;

public class TransportAuthenticationRequest {

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
