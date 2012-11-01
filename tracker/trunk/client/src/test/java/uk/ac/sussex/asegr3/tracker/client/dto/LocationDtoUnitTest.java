package uk.ac.sussex.asegr3.tracker.client.dto;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class LocationDtoUnitTest {

	private static final long TEST_TIMESTAMP = System.currentTimeMillis();
	private static final double TEST_LAT = 4.4564;
	private static final double TEST_LONG = 34.54643;
	private LocationDto candidate;
	
	@Before
	public void before(){
		this.candidate = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIMESTAMP);
	}
	
	@Test
	public void given2IdenticalLocationDtos_whenCallingEquals_thenReturnsTrue(){
		LocationDto anotherSame = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIMESTAMP);
		assertThat(candidate.equals(anotherSame), is(equalTo(true)));
		assertThat(candidate.hashCode(), is(equalTo(anotherSame.hashCode())));
	}
	
	@Test
	public void given2DifferentLatLocationDtos_whenCallingEquals_thenReturnsFalse(){
		LocationDto anotherSame = new LocationDto(TEST_LAT+1, TEST_LONG, TEST_TIMESTAMP);
		assertThat(candidate.equals(anotherSame), is(equalTo(false)));
	}
	
	@Test
	public void given2DifferentLonLocationDtos_whenCallingEquals_thenReturnsFalse(){
		LocationDto anotherSame = new LocationDto(TEST_LAT, TEST_LONG+1, TEST_TIMESTAMP);
		assertThat(candidate.equals(anotherSame), is(equalTo(false)));
	}
	
	@Test
	public void given2DifferentTimestampLocationDtos_whenCallingEquals_thenReturnsFalse(){
		LocationDto anotherSame = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIMESTAMP+1);
		assertThat(candidate.equals(anotherSame), is(equalTo(false)));
	}
	
	@Test
	public void given2DifferentObjects_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate.toString()+"is not equal", candidate.equals(new Object()), is(equalTo(false)));
	}
	
	@Test
	public void given2SameInstancesObjects_whenCallingEquals_thenReturnsTrue(){
		assertThat(candidate.toString()+"is not equal", candidate.equals(candidate), is(equalTo(true)));
	}
	
	@Test
	public void givenNullInstance_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate.toString()+"is not equal", candidate.equals(null), is(equalTo(false)));
	}

}
