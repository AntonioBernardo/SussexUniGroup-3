package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.Serializable;

import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumerProvider;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

public class HttpTransportClientApiFactory implements BatchLocationConsumerProvider, Serializable{
	
	private final HttpTransportClient client;
	private HttpTransportClientApi currentApi;
	
	public static HttpTransportClientApiFactory create(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider){
		return new HttpTransportClientApiFactory(new HttpTransportClient(hostname, logger, networkInfoProvider));
	}
	
	public HttpTransportClientApiFactory(HttpTransportClient client){
		this.client = client;
	}

	public HttpTransportClientApi create(String username, String password){
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

	@Override
	public BatchLocationConsumer getBatchLocationConsumer() {
		if (currentApi == null){
			throw new IllegalStateException("We do not have a authenticated api availanle. Please login");
		}
		return currentApi;
	}
}
