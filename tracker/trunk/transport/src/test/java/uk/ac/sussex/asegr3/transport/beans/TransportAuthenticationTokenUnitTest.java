package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class TransportAuthenticationTokenUnitTest {

	private static final String TEST_USER = "test_user";
	private static final String TEST_SIGNATURE = "abcdef_456";
	private static final long TEST_EXPIRES = 3454536;
	private static final String TEST_TOKEN = "dGVzdF91c2Vy_3454536_YWJjZGVmXzQ1Ng==";
	private static final String TEST_INVALID_TOKEN = "dGVzdF91c2Vy3454536_YWJjZGVmXzQ1Ng==";
	
	private TransportAuthenticationToken candidate;
	
	@Before
	public void before(){
		candidate = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRES);
	}
	
	@Test
	public void givenTransportAuthenticationToken_whenCallingGetToken_correctValueReturned(){
		String token = candidate.getToken();
		
		assertThat(token, is(equalTo(TEST_TOKEN)));
	}
	
	@Test
	public void givenStringToken_whenCallingcreateAuthenticationTokenFromString_correctValueReturned(){
		TransportAuthenticationToken token = TransportAuthenticationToken.createAuthenticationTokenFromString(TEST_TOKEN);
		
		assertThat(token.getUsername(), is(equalTo(TEST_USER)));
		assertThat(token.getSignature(), is(equalTo(TEST_SIGNATURE)));
		assertThat(token.getExpires(), is(equalTo(TEST_EXPIRES)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void givenInvalidStringToken_whenCallingcreateAuthenticationTokenFromString_thenExceptionThrown(){
		TransportAuthenticationToken.createAuthenticationTokenFromString(TEST_INVALID_TOKEN);
	}
}
