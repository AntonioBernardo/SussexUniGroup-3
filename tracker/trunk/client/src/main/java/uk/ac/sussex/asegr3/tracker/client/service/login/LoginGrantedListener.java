package uk.ac.sussex.asegr3.tracker.client.service.login;

import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;

public interface LoginGrantedListener {

	void processLogin(HttpTransportClientApi api);

	void processFailedLogin(Exception e);
}
