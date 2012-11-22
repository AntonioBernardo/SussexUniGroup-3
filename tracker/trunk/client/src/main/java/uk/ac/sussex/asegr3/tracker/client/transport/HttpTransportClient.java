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
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationRequest;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

class HttpTransportClient {
	
	
	private final String url;
	private final Logger logger;
	private final NetworkInfoProvider networkInfoProvider;
	private final HttpClientFactory httpClientFactory;
	
	HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider, HttpClientFactory httpClientFactory){
		this.url = "http://"+hostname.trim();
		this.logger = logger;
		this.networkInfoProvider = networkInfoProvider;
		this.httpClientFactory = httpClientFactory;
	}
	
	public HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider){
		this(hostname, logger, networkInfoProvider, new DefaultHttpClientFactory());
	}
	
	public String login(String username, String password){
	
		// create transport object via getJsonPayloadForAuthenicationRequest
		byte[] object = getJsonPayloadForAuthenicationRequest(password);
		
		// make call to server with transport object
		 HttpPost httppost = new HttpPost(url+"/tracker/batch");
		
		// check status code for errors
		// need to call the class responseble to check
		
		//i need to create a objcet of TransportAuthenticationToken via getTransportAuthenticationToken
		
		// return token from TransportAuthenticationToken
		
		
		return password;
		
	}

	public boolean processBatch(String token, LocationBatch batch) {
		// Create a new HttpClient and Post Header
	    HttpClient httpclient = httpClientFactory.createHttpClient();
	    HttpPost httppost = new HttpPost(url+"/tracker/batch");
	    setupToken(token, httppost);

	    byte[] payload = getJsonPayloadForLocationBatch(batch);
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
	
	private void setupToken(String token, HttpPost httppost) {
		// attach token to http post.
	}

	private TransportAuthenticationToken getTransportAuthenticationToken(byte[] token){
		try {
			JSONObject jsonObject = new JSONObject(new String(token));
			//jsonObject.get
			
			return null;
		} catch (JSONException e) {
			throw new RuntimeException("unable to parse JSON");
		}
		
	}
	
	private byte[] getJsonPayloadForAuthenicationRequest(String password){
		// return json byte for TransportAuthenicationRequest
		
		JSONObject jsonObject = new JSONObject();
		try{
			jsonObject.put(TransportAuthenticationRequest.PASSWORD_TAG, password);
		} catch (JSONException e){
			throw new RuntimeException("Unable to build JSON", e);
		}
		
		return jsonObject.toString().getBytes();
	}

	private byte[] getJsonPayloadForLocationBatch(LocationBatch batch) {
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
			throw new RuntimeException("unable to build batch: "+batch+" to JSON", e);
		}
		
		return jsonObject.toString().getBytes();
	}

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
