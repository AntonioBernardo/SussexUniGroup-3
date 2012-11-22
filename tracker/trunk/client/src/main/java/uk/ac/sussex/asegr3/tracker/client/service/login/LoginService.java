package uk.ac.sussex.asegr3.tracker.client.service.login;

import java.util.concurrent.Executor;

import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class LoginService {

	private final LoginGrantedListener loginGrantedListener;
	private final HttpTransportClientApiFactory apiFactory;
	private final Executor executor;
	private final Logger logger;
	
	public LoginService(LoginGrantedListener loginGrantedListener, Executor executor, 
			HttpTransportClientApiFactory apiFactory, Logger logger){
		this.loginGrantedListener = loginGrantedListener;
		this.executor = executor;
		this.apiFactory = apiFactory;
		this.logger = logger;
	}
	
	public void login(String username, String password){
		executor.execute(new LoginWorker(username, password, apiFactory, loginGrantedListener, logger));
	}
	
	private static class LoginWorker implements Runnable{

		private final String username;
		private final String password;
		private final HttpTransportClientApiFactory apiFactory;
		private final LoginGrantedListener loginGrantedListener;
		private final Logger logger;
		
		public LoginWorker(String username, String password, 
				HttpTransportClientApiFactory apiFactory, LoginGrantedListener loginGrantedListener,
				Logger logger) {
			this.username = username;
			this.password = password;
			this.apiFactory = apiFactory;
			this.loginGrantedListener = loginGrantedListener;
			this.logger = logger;
		}

		@Override
		public void run() {
			try{
				HttpTransportClientApi api = apiFactory.create(username, password);
				loginGrantedListener.processLogin(api);
			} catch (Exception e){
				logger.error(LoginService.class, "error logging in: "+e);
				loginGrantedListener.processFailedLogin(e);
			}
		}
		
	}
}
