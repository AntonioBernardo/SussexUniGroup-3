package uk.ac.sussex.asegr3.transport.beans;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TransportLocationBatchUnitTest {
	
	private TransportLocationBatch candidate;
	private TransportLocation loc;
	
	
	@Before
	public void before(){
		candidate=new TransportLocationBatch(new ArrayList<TransportLocation>());
		loc=new TransportLocation(1, 123.45, 123.46, System.currentTimeMillis());
		candidate.addLocation(loc);
	}
	
	@Test
	public void givenValidTransportLocationBatch_whenCallingGetters_CorrectValuesReturn(){
		assertThat(candidate.getLocations().size(), equalTo(1));
		assertThat(candidate.getLocations().iterator().next(), equalTo(loc));
	}
	
	

}
