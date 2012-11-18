package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationRequest;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

class HttpTransportClient {
	
	
	private static final int DEFAULT_BUFFER_SIZE = 2048;
	private final URI uri;
	private final URL addBatchUri;
	private final URL authenticateRequestUri;
	private final String hostname;
	private final Logger logger;
	private final NetworkInfoProvider networkInfoProvider;
	private final HttpClientFactory httpClientFactory;
	
	HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider, HttpClientFactory httpClientFactory) throws URISyntaxException, MalformedURLException{
		this.hostname = hostname.trim();
		this.uri = new URI("http://"+this.hostname);
		this.addBatchUri = new URL(uri.toString() +"/location/batch");
		this.authenticateRequestUri = new URL(uri.toString()+"/user/");
		
		this.logger = logger;
		this.networkInfoProvider = networkInfoProvider;
		this.httpClientFactory = httpClientFactory;
	}
	
	public HttpTransportClient(String hostname, Logger logger, NetworkInfoProvider networkInfoProvider) throws URISyntaxException, MalformedURLException{
		this(hostname, logger, networkInfoProvider, new DefaultHttpClientFactory());
	}
	
	public String login(String username, String password) throws AuthenticationException{
	
		// create transport object via getJsonPayloadForAuthenicationRequest
		byte[] authenticationPayload = getJsonPayloadForAuthenicationRequest(password);
		
		try{
			Response response = postJsonData(authenticationPayload, new URL(authenticateRequestUri+username+"/authenticate"));
			if (isResponseOk(response)){
				TransportAuthenticationToken token = getTransportAuthenticationToken(response.getContent());
				
				String tokenStr = token.getToken();
				setupToken(tokenStr);
				
				return tokenStr;
			} else{
				throw new AuthenticationException(username);
			}
		} catch (IOException e){
			throw new AuthenticationException(username, e);
		}
		// make call to server with transport object
		
		// check status code for errors
		// need to call the class responseble to check
		
		//i need to create a objcet of TransportAuthenticationToken via getTransportAuthenticationToken
		
		// return token from TransportAuthenticationToken
		
	}
	
	private Response postJsonData(byte[] payload, URL url) throws IOException{
		HttpURLConnection connection = httpClientFactory.createHttpConnection(url);
		try{
		    connection.setDoInput(true);
		    connection.setDoOutput(true);
		    connection.setRequestProperty("Content-Type", "application/json");
		    connection.setRequestProperty("Accepts", "application/json");
		    
		    connection.setFixedLengthStreamingMode(payload.length);
		    OutputStream out = new BufferedOutputStream(connection.getOutputStream());
		    out.write(payload);
		    out.flush();
		    
		    int statusCode = connection.getResponseCode();
		 // check for redirection.
		    if (!url.getHost().equals(connection.getURL().getHost())) {
		    	return new Response(connection.getResponseCode(), new byte[]{});
		    }
		    
		    byte[] content;
		    if (isStatusCodeOk(statusCode)){
			    InputStream in = new BufferedInputStream(connection.getInputStream());
			   
			    
			    // now parse stream
			    
			    ByteArrayOutputStream baos = new ByteArrayOutputStream();
			    byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
			    
			    while(in.read(buffer) != -1){
			    	baos.write(buffer);
			    }
			    content = baos.toByteArray();
			} else{
				content = new byte[]{};
			}
		    
			    
		    
		    return new Response(connection.getResponseCode(), content);
		} finally{
			connection.disconnect();
		}
	}

	public boolean processBatch(String token, LocationBatch batch) {
		// Create a new HttpClient and Post Header
	    
		byte[] payload = getJsonPayloadForLocationBatch(batch);
	    
	    try {
	    	Response response = postJsonData(payload, addBatchUri);
	    	
	        if (!isResponseOk(response)){
	        	// we were able to get a code back but the format is incorrect. This is a proper error at the protocol lever.
	        	logger.error(HttpTransportClient.class, "Unable to send batch for: "+batch+" with payload: "+new String(payload)+": server rejected message");
	        	return false;
	        }
	    } catch (IOException e) {
	    	logger.error(HttpTransportClient.class, "Unable to send batch for: "+batch+" with payload: "+new String(payload)+": "+e.getMessage());
	        return false;
	    }
	    
	    return true;
	}
	
	private boolean isResponseOk(Response response) {
		return isStatusCodeOk(response.getStatusCode());
	}
	
	private boolean isStatusCodeOk(int statusCode){
		return statusCode >= 200 && statusCode < 300; // status code is 2XX
	}

	private void setupToken(String token) {
		
		 CookieManager cookieManager = new CookieManager();
		 CookieHandler.setDefault(cookieManager);
		 
		 HttpCookie cookie = new HttpCookie(TransportAuthenticationToken.AUTHENTICATION_SIGNATURE_COOKIE_NAME, token);
		 cookie.setDomain(hostname);
		 cookie.setPath("/");
		 cookie.setVersion(0);
		 cookieManager.getCookieStore().add(uri, cookie);
	}

	private TransportAuthenticationToken getTransportAuthenticationToken(byte[] token){
		try {
			JSONObject jsonObject = new JSONObject(new String(token));
			String username = jsonObject.getString(TransportAuthenticationToken.USERNAME_TAG);
			String signature = jsonObject.getString(TransportAuthenticationToken.SIGNATURE_TAG);
			long expires = jsonObject.getLong(TransportAuthenticationToken.EXPIRES_TAG);
			
			return new TransportAuthenticationToken(username, signature, expires);
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
		
		return jsonObject.toString().getBytes(Charset.forName("UTF-8"));
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
		
		return jsonObject.toString().getBytes(Charset.forName("UTF-8"));
	}

	public boolean isReady() {
		return networkInfoProvider.getNetworkInfo().isAvailable();
	}
	
	private static class DefaultHttpClientFactory implements HttpClientFactory{

		@Override
		public HttpURLConnection createHttpConnection(URL uri) throws IOException {
			return (HttpURLConnection)uri.openConnection();
		
		}
	}
	
	private static class Response {
		private final int statusCode;
		private final byte[] content;
		
		private Response(int statusCode, byte[] content){
			this.statusCode = statusCode;
			this.content = content;
		}

		public byte[] getContent() {
			return content;
		}

		public int getStatusCode() {
			return statusCode;
		}
	}

}
