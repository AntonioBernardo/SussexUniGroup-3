package uk.ac.sussex.asegr3.tracker.client.transport;

import org.apache.http.client.HttpClient;

public interface HttpClientFactory {

	HttpClient createHttpClient();
}
