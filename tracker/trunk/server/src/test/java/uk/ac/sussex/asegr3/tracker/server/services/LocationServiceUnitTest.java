package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceUnitTest {
	
	private static final String TEST_USERNAME = "testUser";

	private LocationService candidate;
	
	@Mock
	private LocationDao locationDaoMock;
	
	private LocationDTO location;
	
	@Before
	public void before(){
		
		candidate=new LocationService(locationDaoMock);
		location=new LocationDTO(TEST_USERNAME, 0.0, 1.0, 45678);
	}
	
	@Test
	public void givenValidLocation_ThenStored(){
		candidate.storeLocation(location);
		
		verify(locationDaoMock, times(1)).insert(TEST_USERNAME, 0.0, 1.0, 45678);
		
	}
	
	@Test
	public void givenValidLocations_ThenStored(){
		candidate.storeLocations(Arrays.asList(location, location));
		
		verify(locationDaoMock, times(2)).insert(TEST_USERNAME, 0.0, 1.0, 45678);
		
	}
	
	@Test
	public void givenNoLocations_ThenStored(){
		candidate.storeLocations(null);
		
		verify(locationDaoMock, never()).insert(anyString(), anyDouble(), anyDouble(), anyLong());
		
	}

}
