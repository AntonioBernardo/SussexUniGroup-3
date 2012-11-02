package uk.ac.sussex.asegr3.tracker.server.api;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.dao.TrackerDao;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

@RunWith(MockitoJUnitRunner.class)
public class TrackerResourceUnitTest {

	private TrackerResource candidate;
	
	@Mock
	private TrackerDao trackerDaoMock;
	
	private TransportLocation loc;
	private TransportLocationBatch locBatch;
	
	@Before
	public void before(){
		candidate = new TrackerResource(new LocationService(trackerDaoMock));
		loc=new TransportLocation(0.0, 1.0, 1234567890);
		locBatch=new TransportLocationBatch(new ArrayList<TransportLocation>());
		locBatch.addLocation(loc);
	}
	
	@Test
	public void givenValidLocation_whenCallingAddLocation_thenAppropriateServiceCalled(){
		candidate.addLocation(new TransportLocation(1.0, 2.0, 45678));
		candidate.addLocations(locBatch);
		verify(trackerDaoMock, times(1)).insert(1, 1.0, 2.0, 45678);
		verify(trackerDaoMock, times(1)).insert(1, 0.0, 1.0, 1234567890);
	}
}
