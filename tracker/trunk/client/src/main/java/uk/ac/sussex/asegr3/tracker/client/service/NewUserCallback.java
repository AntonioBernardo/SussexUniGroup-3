package uk.ac.sussex.asegr3.tracker.client.service;

import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;

public interface NewUserCallback {

	void processNewUser(HttpTransportClientApi api);

	void processFailedSignup(Exception e);
}
