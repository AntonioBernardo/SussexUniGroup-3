package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportNewUserRequest extends AbstractTransportUserRequest{

	public static final String PASSWORD_TAG = "password";
	
	@XmlElement(name=PASSWORD_TAG, required=true)
	private String password;
	
	public TransportNewUserRequest(){
		
	}
	
	public TransportNewUserRequest(String email, 
			String password,
			String name,
			String surname,
			int age,
			Gender gender,
			String aboutYou,
			String interests) throws InvalidEmailException{
		
		super(email, name, surname, age, gender, aboutYou, interests);
		setPassword(password);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
