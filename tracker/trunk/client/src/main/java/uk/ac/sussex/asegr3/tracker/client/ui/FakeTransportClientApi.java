package uk.ac.sussex.asegr3.tracker.client.ui;

import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;

public class FakeTransportClientApi implements HttpTransportClientApi {

	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean processBatch(LocationBatch batch) {
		// TODO Auto-generated method stub
		return false;
	}

}
