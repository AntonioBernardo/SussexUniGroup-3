package uk.ac.sussex.asegr3.tracker.server.domainmodel;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO.Gender;

public class UserDTO {

	public static enum Gender {
		MALE,
		FEMALE;

		public static Gender valueOfById(String id) {
			if (id.equals("M")){
				return MALE;
			} else if (id.equals("F")){
				return FEMALE;
			} else{
				throw new IllegalArgumentException("Unknown id: "+id);
			}
		}
		public static String getIdFromValue(Gender gender){
			if (gender == MALE){
				return "M";
			} else{
				return "F";
			}
		}
	}
	private final String email;
	private final String name;
	private final String surname;
	private final int age;
	private final Gender gender;
	private final String about;
	private final String interests;
	private final long lastLogginDate;
	private final long signupDate;
	
	public UserDTO(String email,
				   String name,
				   String surname,
				   int age,
				   Gender gender,
				   String about,
				   String interests,
				   long lastLogginDate,
				   long signupDate){
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.age = age;
		this.gender = gender;
		this.about = about;
		this.interests = interests;
		this.lastLogginDate = lastLogginDate;
		this.signupDate = signupDate;
		
	}

	public String getEmail() {
		return email;
	}
	public String getName() {
		return name;
	}
	public String getSurname() {
		return surname;
	}
	public int getAge() {
		return age;
	}
	public Gender getGender() {
		return gender;
	}
	
	public String getGenderId(){
		return Gender.getIdFromValue(gender);
	}
	public String getAbout() {
		return about;
	}
	public String getInterests() {
		return interests;
	}

	public long getLastLogginDate() {
		return lastLogginDate;
	}

	public long getSignupDate() {
		return signupDate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((about == null) ? 0 : about.hashCode());
		result = prime * result + age;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result
				+ ((interests == null) ? 0 : interests.hashCode());
		result = prime * result
				+ (int) (lastLogginDate ^ (lastLogginDate >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + (int) (signupDate ^ (signupDate >>> 32));
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof UserDTO))
			return false;
		UserDTO other = (UserDTO) obj;
		if (about == null) {
			if (other.about != null)
				return false;
		} else if (!about.equals(other.about))
			return false;
		if (age != other.age)
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (gender != other.gender)
			return false;
		if (interests == null) {
			if (other.interests != null)
				return false;
		} else if (!interests.equals(other.interests))
			return false;
		if (lastLogginDate != other.lastLogginDate)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (signupDate != other.signupDate)
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}
}
