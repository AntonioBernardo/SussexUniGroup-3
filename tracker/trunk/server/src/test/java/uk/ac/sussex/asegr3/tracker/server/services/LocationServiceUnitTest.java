package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import uk.ac.sussex.asegr3.tracker.server.dao.TrackerDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;

public class LocationServiceUnitTest {
	
	private LocationService candidate;
	
	@Mock
	private TrackerDao locationDaoMock;
	
	private LocationDTO location;
	
	@Before
	public void before(){
		
		candidate=new LocationService(locationDaoMock);
		location=new LocationDTO(0.0, 1.0, 45678);
	}
	
//	@Test
//	public void givenValidLocation_ThenStored(){
//		candidate.storeLocation(location);
//		
//		verify(locationDaoMock, times(1)).insert(1, 0.0, 1.0, 45678);
//		
//	}
	
	

}
