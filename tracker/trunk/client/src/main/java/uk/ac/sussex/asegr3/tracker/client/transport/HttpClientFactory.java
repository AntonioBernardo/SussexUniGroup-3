package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpClientFactory extends Serializable{

	HttpURLConnection createHttpConnection(URL uri) throws IOException;
}
