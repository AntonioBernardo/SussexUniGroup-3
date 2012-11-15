package uk.ac.sussex.asegr3.tracker.server.services.authentication;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceUnitTest {

	private static final long TEST_TIME = 1000;
	private static final String TEST_USERNAME = "testUser";
	private static final String TEST_PASSWORD = "testPassword";
	private static final String TEST_ENCRYPTED_PASSWORD = "99d541d0226afdb95b5d8432e9e640e49310ca13f0f9fdb60e415200a1523fe6";
	private static final String TEST_INVALID_ENCRYPTED_PASSWORD = "dd";
	private static final String TEST_SIGNATURE = "06df2b3a496e90d8bc0db47ea0e70416f4b7bbf8";
	
	private AuthenticationService candidate;
	
	@Mock
	private UserDao userDaoMock;
	
	@Before
	public void before(){
		candidate = new AuthenticationService(userDaoMock, TEST_TIME);
	}
	
	@Test
	public void givenValidCredentials_whenCallingAuthenticateUser_thenValidTokenReturned(){
		when(userDaoMock.getPasswordForUser(TEST_USERNAME)).thenReturn(TEST_ENCRYPTED_PASSWORD);
		long expectedExpiry = TEST_TIME + System.currentTimeMillis();
		AuthenticationToken token = candidate.authenticateUser(TEST_USERNAME, TEST_PASSWORD);
		
		assertThat(token, is(not(nullValue())));
		assertThat(token.getExpires(), is(greaterThanOrEqualTo(expectedExpiry)));
		assertThat(token.getUsername(), is(equalTo(TEST_USERNAME)));
		assertThat(token.getSignature(), is(equalTo(TEST_SIGNATURE)));
	}
	
	@Test(expected=SecurityViolationException.class)
	public void givenInvalidCredentials_whenCallingAuthenticateUser_thenExceptionThrown(){
		when(userDaoMock.getPasswordForUser(TEST_USERNAME)).thenReturn(TEST_INVALID_ENCRYPTED_PASSWORD);
		candidate.authenticateUser(TEST_USERNAME, TEST_PASSWORD);
	}
}
