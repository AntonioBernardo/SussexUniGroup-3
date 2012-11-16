package uk.ac.sussex.asegr3.tracker.server.services.authentication;

import java.security.SignatureException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.Clock;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationServiceUnitTest {

	private static final long TEST_TIME = 1000;
	private static final String TEST_USERNAME = "testUser";
	private static final String TEST_PASSWORD = "testPassword";
	private static final String TEST_ENCRYPTED_PASSWORD = "99d541d0226afdb95b5d8432e9e640e49310ca13f0f9fdb60e415200a1523fe6";
	private static final String TEST_INVALID_ENCRYPTED_PASSWORD = "dd";
	private static final String TEST_SIGNATURE = "a9665f2124162930f774d7d62d1979ca4e329dd1";
	private static final Long TEST_CURRENT_TIME = 1234567890L;
	
	private AuthenticationService candidate;
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private Clock clockMock;
	
	@Before
	public void before(){
		when(clockMock.getCurrentTime()).thenReturn(TEST_CURRENT_TIME);
		candidate = new AuthenticationService(userDaoMock, TEST_TIME, clockMock);
	}
	
	@Test
	public void givenValidCredentials_whenCallingAuthenticateUser_thenValidTokenReturned(){
		when(userDaoMock.getPasswordForUser(TEST_USERNAME)).thenReturn(TEST_ENCRYPTED_PASSWORD);
		long expectedExpiry = TEST_TIME + TEST_CURRENT_TIME;
		AuthenticationToken token = candidate.authenticateUser(TEST_USERNAME, TEST_PASSWORD);
		
		assertThat(token, is(not(nullValue())));
		assertThat(token.getExpires(), is(equalTo(expectedExpiry)));
		assertThat(token.getUsername(), is(equalTo(TEST_USERNAME)));
		assertThat(token.getSignature(), is(equalTo(TEST_SIGNATURE)));
	}
	
	@Test(expected=SecurityViolationException.class)
	public void givenInvalidCredentials_whenCallingAuthenticateUser_thenExceptionThrown(){
		when(userDaoMock.getPasswordForUser(TEST_USERNAME)).thenReturn(TEST_INVALID_ENCRYPTED_PASSWORD);
		candidate.authenticateUser(TEST_USERNAME, TEST_PASSWORD);
	}
	
	@Test
	public void givenValidToken_whenCallingValidateToken_thenReturnsTrue() throws SignatureException{
		TransportAuthenticationToken validToken = new TransportAuthenticationToken(TEST_USERNAME, TEST_SIGNATURE,TEST_CURRENT_TIME+TEST_TIME);
		assertThat(candidate.validateToken(validToken), is(equalTo(true)));
	}
	
	@Test
	public void givenInvalidToken_whenCallingValidateToken_thenReturnsTrue() throws SignatureException{
		TransportAuthenticationToken invalidToken = new TransportAuthenticationToken(TEST_USERNAME, TEST_SIGNATURE+43,TEST_CURRENT_TIME+TEST_TIME);
		assertThat(candidate.validateToken(invalidToken), is(equalTo(false)));
	}
	
	@Test
	public void givenExpiredValidToken_whenCallingValidateToken_thenReturnsTrue() throws SignatureException{
		TransportAuthenticationToken validToken = new TransportAuthenticationToken(TEST_USERNAME, TEST_SIGNATURE,TEST_CURRENT_TIME);
		assertThat(candidate.validateToken(validToken), is(equalTo(false)));
	}
}
