package uk.ac.sussex.asegr3.tracker.client.transport;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.client.dataobject.Comment;
import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.BatchLocationConsumer;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;

public interface HttpTransportClientApi extends BatchLocationConsumer{
	
	String getLoggedInUser();
	boolean processBatch(LocationBatch batch);
	
	List<LocationDto> getNearbyLocations();
	// other api calls to server
	
	boolean postComment(Comment comment);
}
