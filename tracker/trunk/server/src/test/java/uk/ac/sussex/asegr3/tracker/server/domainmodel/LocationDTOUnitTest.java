package uk.ac.sussex.asegr3.tracker.server.domainmodel;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;


public class LocationDTOUnitTest {

	private static final long TEST_TIMESTAMP = System.currentTimeMillis();
	private static final double TEST_LAT = 4.4564;
	private static final double TEST_LONG = 34.54643;
	private LocationDTO candidate;
	
	@Before
	public void before(){
		candidate=new LocationDTO(TEST_LAT, TEST_LONG, TEST_TIMESTAMP);
	}
	
	@Test
	public void givenALocation_ReturnsSameValues(){
		assertThat(candidate.getLatitude(), equalTo(TEST_LAT));
		assertThat(candidate.getLongitude(), equalTo(TEST_LONG));
		assertThat(candidate.getTimeStamp(), equalTo(TEST_TIMESTAMP));
	}
}
