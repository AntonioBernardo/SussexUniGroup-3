package uk.ac.sussex.asegr3.tracker.server.domainmodel;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class LocationDTOUnitTest {

	private static final long TEST_TIMESTAMP = System.currentTimeMillis();
	private static final double TEST_LAT = 4.4564;
	private static final double TEST_LONG = 34.54643;
	private static final String TEST_USERNAME = "testUser";
	private LocationDTO candidate;
	
	@Before
	public void before(){
		candidate=new LocationDTO(TEST_USERNAME, TEST_LAT, TEST_LONG, TEST_TIMESTAMP, Collections.<CommentDTO>emptyList());
	}
	
	@Test
	public void givenALocation_ReturnsSameValues(){
		assertThat(candidate.getLatitude(), equalTo(TEST_LAT));
		assertThat(candidate.getLongitude(), equalTo(TEST_LONG));
		assertThat(candidate.getTimestamp(), equalTo(TEST_TIMESTAMP));
	}
}
