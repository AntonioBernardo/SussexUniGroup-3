package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

public class HttpTransportClient implements BatchLocationConsumer{
	
	private final String url;
	private final Logger logger;
	private final NetworkInfoProvider networkInfoProvider;
	private final HttpClientFactory httpClientFactory;
	
	HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider, HttpClientFactory httpClientFactory){
		this.url = "http://"+hostname.trim()+"/tracker/batch";
		this.logger = logger;
		this.networkInfoProvider = networkInfoProvider;
		this.httpClientFactory = httpClientFactory;
	}
	
	public HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider){
		this(hostname, logger, networkInfoProvider, new DefaultHttpClientFactory());
	}

	@Override
	public boolean processBatch(LocationBatch batch) {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = httpClientFactory.createHttpClient();
	    HttpPost httppost = new HttpPost(url);

	    byte[] payload = getJsonPayload(batch);
	    try {
	        // Add your data
	    	
	    	HttpEntity entity = new ByteArrayEntity(payload);
	        httppost.setEntity(entity);
	        httppost.setHeader("Content-Type", "application/json");
	        httppost.setHeader("Accept", "application/json");

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        if (response.getStatusLine().getStatusCode() >= 200 && response.getStatusLine().getStatusCode() < 300){ // 2XX code returned
	        	return true;
	        }
	        else {
	        	// we were able to get a code back but the format is incorrect. This is a proper error at the protocol lever.
	        	logger.error(HttpTransportClient.class, "Unable to send batch for: "+batch+" with payload: "+new String(payload)+": server rejected message");
	        	return false;
	        }
	    } catch (ClientProtocolException e) {
	    	logger.error(HttpTransportClient.class, "Unable to send batch for: "+batch+" with payload: "+new String(payload)+": "+e.getMessage());
	    	return false;
	    } catch (IOException e) {
	    	logger.error(HttpTransportClient.class, "Unable to send batch for: "+batch+" with payload: "+new String(payload)+": "+e.getMessage());
	        return false;
	    }
	}

	private byte[] getJsonPayload(LocationBatch batch) {
		JSONObject jsonObject = new JSONObject();
		JSONArray locationsArray = new JSONArray();
		try{
			for (LocationDto location: batch.getLocations()){
				JSONObject locationJson = new JSONObject();
				locationJson.put(TransportLocation.LATTITUDE_TAG, location.getLat());
				locationJson.put(TransportLocation.LONGITUDE_TAG, location.getLng());
				locationJson.put(TransportLocation.TIMESTAMP_TAG, location.getTimestamp());
				locationsArray.put(locationJson);
			}
			
			jsonObject.put(TransportLocationBatch.LOCATIONS_TAG, locationsArray);
		} catch (JSONException e){
			throw new RuntimeException("unable to parse batch: "+batch+" to JSON", e);
		}
		
		return jsonObject.toString().getBytes();
	}

	@Override
	public boolean isReady() {
		return networkInfoProvider.getNetworkInfo().isAvailable();
	}
	
	private static class DefaultHttpClientFactory implements HttpClientFactory{

		@Override
		public HttpClient createHttpClient() {
			return new DefaultHttpClient();
		}
		
	}

}
