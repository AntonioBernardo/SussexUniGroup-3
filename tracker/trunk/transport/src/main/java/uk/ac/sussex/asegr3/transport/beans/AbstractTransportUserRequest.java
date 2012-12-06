package uk.ac.sussex.asegr3.transport.beans;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class AbstractTransportUserRequest {

	public static enum Gender {
		MALE,
		FEMALE;
	}
	public static final String EMAIL_TAG = "email";
	public static final String NAME_TAG = "name";
	public static final String SURNAME_TAG = "surname";
	public static final String AGE_TAG = "age";
	public static final String GENDER_TAG = "gender";
	public static final String ABOUT_YOU_TAG = "aboutYou";
	public static final String INTERESTS_TAG = "interests";
	private static final String EMAIL_REGEX = ".+@.+\\..+";
	
	@XmlElement(name=EMAIL_TAG, required=true)
	private String email;
	
	@XmlElement(name=NAME_TAG, required=true)
	private String name;
	
	@XmlElement(name=SURNAME_TAG, required=true)
	private String surname;
	
	@XmlElement(name=AGE_TAG, required=true)
	private int age;
	
	@XmlElement(name=GENDER_TAG, required=true)
	private Gender gender;
	
	@XmlElement(name=ABOUT_YOU_TAG, required=true)
	private String aboutYou;
	
	@XmlElement(name=INTERESTS_TAG, required=true)
	private String interests;
	
	public AbstractTransportUserRequest(String email, 
								   String name,
								   String surname,
								   int age,
								   Gender gender,
								   String aboutYou,
								   String interests) throws InvalidEmailException{
		this();
		setEmail(email);
		setName(name);
		setSurname(surname);
		setAge(age);
		setGender(gender);
		setAboutYou(aboutYou);
		setInterests(interests);
	}
	public AbstractTransportUserRequest(){
		
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) throws InvalidEmailException {
		validateEmail(email);
		this.email = email;
	}
	private void validateEmail(String email) throws InvalidEmailException {

		if (!email.matches(EMAIL_REGEX)){
			throw new InvalidEmailException("email: "+email+" is not a valid email address");
		}
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public String getAboutYou() {
		return aboutYou;
	}
	public void setAboutYou(String aboutYou) {
		this.aboutYou = aboutYou;
	}
	public String getInterests() {
		return interests;
	}
	public void setInterests(String interests) {
		this.interests = interests;
	}
	
	
}
