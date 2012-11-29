package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransportNewUserRequestUnitTest {

	private static final String TEST_USERNAME = "testUser";
	private static final String TEST_PASSWORD = "testPassword";
	
	private TransportNewUserRequest candidate;
	
	@Before
	public void before(){
		this.candidate = new TransportNewUserRequest(TEST_USERNAME, TEST_PASSWORD);
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetUserName_thenCorrectStringReturned(){
		assertThat(candidate.getUsername(), is(equalTo(TEST_USERNAME)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetPassword_thenCorrectStringReturned(){
		assertThat(candidate.getPassword(), is(equalTo(TEST_PASSWORD)));
	}
}
