package uk.ac.sussex.asegr3.tracker.client.ui;

import java.util.concurrent.Executor;

import uk.ac.sussex.asegr3.tracker.client.service.login.LoginGrantedListener;
import uk.ac.sussex.asegr3.tracker.client.service.login.LoginService;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class LoginServiceFactory {

	private static final String DEFAULT_HOSTNAME = "localhost";

	public LoginService create(LoginGrantedListener loginGrantedListener, Activity activity, Executor executor, Logger logger){
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(DEFAULT_HOSTNAME, logger, createNetworkInfoProvider(activity));
		
		LoginService loginService = new LoginService(loginGrantedListener, executor, apiFactory, logger);

		return loginService;
	}
	private NetworkInfo getNetworkInfo(Activity activity) {
		 ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		 return connectivityManager.getActiveNetworkInfo();
	}
	
	private NetworkInfoProvider createNetworkInfoProvider(final Activity activity){
		return new NetworkInfoProvider(){

			@Override
			public NetworkInfo getNetworkInfo() {
				return LoginServiceFactory.this.getNetworkInfo(activity);
			}
			
		};
	}
}