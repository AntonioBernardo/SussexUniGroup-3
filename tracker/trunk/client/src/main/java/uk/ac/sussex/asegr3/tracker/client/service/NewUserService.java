package uk.ac.sussex.asegr3.tracker.client.service;

import java.util.concurrent.Executor;

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

	public void signUp(String username, String password, String confirmedPassword){
		executor.execute(new SignUpUserTask(apiFactory, newUserCallback, username, password, confirmedPassword));
	}
	
	private static class SignUpUserTask implements Runnable{

		private final String username;
		private final String password;
		private final String confirmedPassword;
		private final HttpTransportClientApiFactory apiFactory;
		private final NewUserCallback newUserCallback;
		
		public SignUpUserTask(HttpTransportClientApiFactory apiFactory, NewUserCallback newUserCallback, String username, String password, String confirmedPassword) {
			this.apiFactory = apiFactory;
			this.newUserCallback = newUserCallback;
			this.username = username;
			this.password = password;
			this.confirmedPassword = confirmedPassword;
		}

		@Override
		public void run() {
			if (confirmedPassword.equals(password)){
				try {
					HttpTransportClientApi api = apiFactory.createFromNewUser(username, password);
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