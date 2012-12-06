package uk.ac.sussex.asegr3.tracker.client.dto;

public class UserProfileDto {

	public static final int GENDER_MALE = 0;
	public static final int GENDER_FEMALE = 1;
	
	private String email;
	private String name;
	private String surname;
	private int age;
	private int gender = GENDER_MALE;
	private String about;
	private String interests;
	private long lastLoggedIn;
	private long signUpDate;
	
	public UserProfileDto(){
		
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getInterests() {
		return interests;
	}

	public void setInterests(String interests) {
		this.interests = interests;
	}

	public long getLastLoggedIn() {
		return lastLoggedIn;
	}

	public void setLastLoggedIn(long lastLoggedIn) {
		this.lastLoggedIn = lastLoggedIn;
	}

	public long getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(long signUpDate) {
		this.signUpDate = signUpDate;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		if (gender != GENDER_FEMALE && gender != GENDER_MALE){
			throw new IllegalArgumentException("gender: "+gender+" is not a valid gender descriptor");
		}
		this.gender = gender;
	}
}
