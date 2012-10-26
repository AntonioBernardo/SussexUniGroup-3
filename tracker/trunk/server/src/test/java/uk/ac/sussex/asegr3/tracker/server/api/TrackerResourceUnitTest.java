package uk.ac.sussex.asegr3.tracker.server.api;

import org.junit.Before;
import org.junit.Test;

public class TrackerResourceUnitTest {

	private TrackerResource candidate;
	
	@Before
	public void before(){
		candidate = new TrackerResource();
	}
	
	@Test
	public void givenValidLocation_whenCallingAddLocation_thenAppropriateServiceCalled(){
		candidate.addLocation();
	}
}
