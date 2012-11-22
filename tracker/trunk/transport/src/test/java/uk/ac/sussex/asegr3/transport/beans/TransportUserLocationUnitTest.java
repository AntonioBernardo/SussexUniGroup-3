package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class TransportUserLocationUnitTest {

	private static final String TEST_USERNAME = "testUser";
	private static final double TEST_LAT = 546;
	private static final double TEST_LONG = 765;
	private static final long TEST_TIME = 45765467;
	private TransportUserLocation candidate;
	
	@Before
	public void before(){
		TransportLocation location = new TransportLocation(TEST_LAT, TEST_LONG, TEST_TIME);
		candidate = new TransportUserLocation(TEST_USERNAME, location);
	}
	
	@Test
	public void givenDefaultValues_whenCallingGetUser_thenCorrectUserReturned(){
		assertThat(candidate.getUsername(), is(equalTo(TEST_USERNAME)));
	}
	
	@Test
	public void givenDefaultValues_whenCallingGetLocation_thenCorrectUserReturned(){
		assertThat(candidate.getLocation().getLattitude(), is(equalTo(TEST_LAT)));
		assertThat(candidate.getLocation().getLongitude(), is(equalTo(TEST_LONG)));
		assertThat(candidate.getLocation().getTimestamp(), is(equalTo(TEST_TIME)));
	}
}
