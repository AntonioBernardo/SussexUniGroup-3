package uk.ac.sussex.asegr3.tracker.client.transport;

import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;

public interface HttpTransportClientApi extends BatchLocationConsumer{

	boolean processBatch(LocationBatch batch);
	
	// other api calls to server
}
