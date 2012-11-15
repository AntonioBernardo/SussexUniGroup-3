package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class TransportAuthenticationRequestUnitTest {

	private static final String TEST_PASSWORD = "TEST_PASSWORD";
	private TransportAuthenticationRequest candidate;
	
	@Before
	public void before(){
		candidate = new TransportAuthenticationRequest(TEST_PASSWORD);
	}
	
	@Test
	public void givenDefaultRequest_whenCallingGetPassword_thenCorrectPasswordReturned(){
		assertThat(candidate.getPassword(), is(equalTo(TEST_PASSWORD)));
	}
}
