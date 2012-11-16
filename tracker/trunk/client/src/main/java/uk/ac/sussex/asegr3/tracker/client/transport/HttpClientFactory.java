package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public interface HttpClientFactory {

	HttpURLConnection createHttpConnection(URL uri) throws IOException;
}
