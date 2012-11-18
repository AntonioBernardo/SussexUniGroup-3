package uk.ac.sussex.asegr3.tracker.client.transport;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class HttpTransportClientApiFactory {
	
	private final HttpTransportClient client;
	private HttpTransportClientApi currentApi;
	
	public static HttpTransportClientApiFactory create(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider) throws MalformedURLException, URISyntaxException{
		return new HttpTransportClientApiFactory(new HttpTransportClient(hostname, logger, networkInfoProvider));
	}
	
	public HttpTransportClientApiFactory(HttpTransportClient client){
		this.client = client;
	}

	public HttpTransportClientApi create(String username, String password) throws AuthenticationException{
		final String token = client.login(username, password);
		
		currentApi =  new HttpTransportClientApi(){

			@Override
			public boolean processBatch(LocationBatch batch) {

				return client.processBatch(token, batch);
			}

			@Override
			public boolean isReady() {
				return client.isReady();
			}
			
		};
		return currentApi;
	}
}
