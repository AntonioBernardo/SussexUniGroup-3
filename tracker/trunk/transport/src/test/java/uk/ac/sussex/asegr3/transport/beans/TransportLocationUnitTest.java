package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class TransportLocationUnitTest {
	
	private static final double LATT_TEST=1234.567890;
	private static final double LONG_TEST=0987.654321;
	private static final long TIME_TEST=System.currentTimeMillis();
	
	private TransportLocation candidate;
	
	@Before
	public void before(){	
		this.candidate=new TransportLocation(1, LATT_TEST, LONG_TEST, TIME_TEST);
	}

	
	@Test
	public void givenTransportLocationObject_whenCallingGet_RetrievesSameData(){
		assertThat(candidate.getLattitude(), equalTo(LATT_TEST));
		assertThat(candidate.getLongitude(), equalTo(LONG_TEST));
		assertThat(candidate.getTimestamp(), equalTo(TIME_TEST));
		
	}
}
