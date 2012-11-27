package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import uk.ac.sussex.asegr3.transport.beans.Base64Encoder;

public class HttpTransportClientApiFactory implements Serializable{
	
	private final HttpTransportClient client;
	private static HttpTransportClientApi CURRENT_API;
	
	public static HttpTransportClientApiFactory create(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider, Base64Encoder encoder) throws MalformedURLException, URISyntaxException{
		return new HttpTransportClientApiFactory(new HttpTransportClient(hostname, logger, networkInfoProvider, encoder));
	}
	
	public HttpTransportClientApiFactory(HttpTransportClient client){
		this.client = client;
	}


	public HttpTransportClientApi create(String username, String password) throws AuthenticationException{

		final String token = client.login(username, password);
		
		CURRENT_API =  new TokenBasedHttpTransportClientApi(client, token);
			
		return CURRENT_API;
	}
	
	public HttpTransportClientApi createFromNewUser(String username, String password) throws NewUserSignupException{

		final String token = client.signupUser(username, password);
		
		CURRENT_API =  new TokenBasedHttpTransportClientApi(client, token);
			
		return CURRENT_API;
	}
	
	public static HttpTransportClientApi getCurrentApi(){
		return CURRENT_API;
	}
	
	private static class TokenBasedHttpTransportClientApi implements HttpTransportClientApi, Serializable{

		private final HttpTransportClient client;
		private final String token;
		
		private TokenBasedHttpTransportClientApi(HttpTransportClient client, String token){
			this.client = client;
			this.token = token;
		}
		@Override
		public boolean processBatch(LocationBatch batch) {

			return client.processBatch(token, batch);
		}

		@Override
		public boolean isReady() {
			return client.isReady();
		}
		
	}
}
