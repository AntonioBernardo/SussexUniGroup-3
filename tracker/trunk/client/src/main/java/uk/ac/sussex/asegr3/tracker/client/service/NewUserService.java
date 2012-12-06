package uk.ac.sussex.asegr3.tracker.client.service;

import java.util.concurrent.Executor;

import uk.ac.sussex.asegr3.tracker.client.dto.UserProfileDto;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.transport.NewUserSignupException;

public class NewUserService {
	
	private final Executor executor;
	private final NewUserCallback newUserCallback;
	private final HttpTransportClientApiFactory apiFactory;
	
	public NewUserService(HttpTransportClientApiFactory apiFactory, Executor executor, NewUserCallback newUserCallback){
		this.apiFactory = apiFactory;
		this.executor = executor;
		this.newUserCallback = newUserCallback;
	}

	public void signUp(String username, String password, String confirmedPassword, String name, String surname, int age, int gender, String aboutUs, String interests){
		executor.execute(new SignUpUserTask(apiFactory, newUserCallback, username, password, confirmedPassword, name, surname, age, gender, aboutUs, interests));
	}
	
	private static class SignUpUserTask implements Runnable{

		private final String username;
		private final String password;
		private final String name;
		private final String surname;
		private final int age;
		private final int gender;
		private final String aboutUs;
		private final String interests;
		
		private final String confirmedPassword;
		private final HttpTransportClientApiFactory apiFactory;
		private final NewUserCallback newUserCallback;
		
		public SignUpUserTask(HttpTransportClientApiFactory apiFactory, NewUserCallback newUserCallback, String username, String password, String confirmedPassword, String name, String surname, int age, int gender, String aboutUs, String interests) {
			this.apiFactory = apiFactory;
			this.newUserCallback = newUserCallback;
			this.username = username;
			this.password = password;
			this.confirmedPassword = confirmedPassword;
			this.name = name;
			this.surname = surname;
			this.aboutUs = aboutUs;
			this.interests = interests;
			this.age = age;
			this.gender = gender;
		}

		@Override
		public void run() {
			if (confirmedPassword.equals(password)){
				try {
					UserProfileDto newUser = new UserProfileDto();
					newUser.setEmail(username);
					newUser.setName(name);
					newUser.setSurname(surname);
					newUser.setAge(age);
					newUser.setGender(gender);
					newUser.setInterests(interests);
					HttpTransportClientApi api = apiFactory.createFromNewUser(newUser, password);
					newUserCallback.processNewUser(api);
				} catch (NewUserSignupException e) {
					newUserCallback.processFailedSignup(e);
				}
			} else{
				newUserCallback.processFailedSignup(new IllegalArgumentException("passwords do not match. Please try again"));
			}
		}
		
	}
}