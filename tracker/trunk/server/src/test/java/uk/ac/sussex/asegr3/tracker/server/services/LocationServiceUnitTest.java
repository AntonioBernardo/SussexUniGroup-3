package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceUnitTest {
	
	private static final String TEST_USERNAME = "testUser";

	private LocationService candidate;
	
	@Mock
	private LocationDao locationDaoMock;
	
	@Mock
	private CommentDao commentDaoMock;
	
	private LocationDTO location;
	
	@Before
	public void before(){
		
		candidate=new LocationService(locationDaoMock, commentDaoMock);
		location=new LocationDTO(1, TEST_USERNAME, 0.0, 1.0, 45678, Collections.<CommentDTO>emptyList());
	}
	
	@Test
	public void givenValidLocation_ThenStored(){
		candidate.storeLocation(TEST_USERNAME, location);
		
		verify(locationDaoMock, times(1)).insert(TEST_USERNAME, 0.0, 1.0, 45678);
		
	}
	
	@Test
	public void givenValidLocations_ThenStored(){
		candidate.storeLocations(TEST_USERNAME, Arrays.asList(location, location));
		
		verify(locationDaoMock, times(1)).insert(TEST_USERNAME, Arrays.asList(location, location));
		
	}
	
	@Test
	public void givenNoLocations_ThenStored(){
		candidate.storeLocations(TEST_USERNAME, null);
		
		verify(locationDaoMock, never()).insert(anyString(), anyDouble(), anyDouble(), anyLong());
		
	}

}
