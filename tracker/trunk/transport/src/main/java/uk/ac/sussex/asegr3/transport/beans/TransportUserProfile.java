package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportUserProfile extends AbstractTransportUserRequest {

	private static final String SIGN_UP_DATE_TAG = "signupDate";
	private static final String LAST_LOGGED_IN_TAG = "lastLoggedIn";

	@XmlElement(name=SIGN_UP_DATE_TAG, required=true)
	private long signupDate;
	
	@XmlElement(name=LAST_LOGGED_IN_TAG, required=true)
	private long lastLoggedIn;
	
	public TransportUserProfile(String email, 
			   String name,
			   String surname,
			   int age,
			   Gender gender,
			   String aboutYou,
			   String interests,
			   long signupDate,
			   long lastLoggedIn) throws InvalidEmailException{
		super(email, name, surname, age, gender, aboutYou, interests);
		this.setSignupDate(signupDate);
		this.setLastLoggedIn(lastLoggedIn);
	}

	public long getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(long signupDate) {
		this.signupDate = signupDate;
	}

	public long getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(long lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}
	
}
