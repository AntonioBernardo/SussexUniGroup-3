package uk.ac.sussex.asegr3.tracker.security;

import java.security.SignatureException;

import javax.ws.rs.WebApplicationException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;

import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserAuthenticatorUnitTest {

	private static final String TEST_USER = "testUserName";

	private static final String TEST_SIGNATURE = "testSig";

	private static final long TEST_EXPIRATION = 43533L;

	private UserAuthenticator candidate;
	
	@Mock
	private AuthenticationService authServiceMock;
	
	@Before
	public void before(){
		candidate = new UserAuthenticator(authServiceMock);
	}
	
	@Test
	public void givenValidatedCredential_whenCallingAuthenticate_thenReturnLoggedInUser() throws AuthenticationException, SignatureException{
		TransportAuthenticationToken credentials = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRATION);
		when(authServiceMock.validateToken(credentials)).thenReturn(true);
		
		Optional<LoggedInUser> user = candidate.authenticate(credentials);
		
		assertThat(user.isPresent(), is(equalTo(true)));
		assertThat(user.get(), is(not(nullValue())));
		assertThat(user.get().getUsername(), is(equalTo(TEST_USER)));
		assertThat(user.get().getSessionExpires(), is(equalTo(TEST_EXPIRATION)));
	}
	
	@Test
	public void givenInvalidatedCredential_whenCallingAuthenticate_thenNullReturned() throws AuthenticationException, SignatureException{
		TransportAuthenticationToken credentials = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRATION);
		when(authServiceMock.validateToken(credentials)).thenReturn(false);
		
		Optional<LoggedInUser> user = candidate.authenticate(credentials);
		
		assertThat(user.isPresent(), is(equalTo(false)));
	}
	
	@Test(expected=WebApplicationException.class)
	public void givenExceptionThrown_whenCallingAuthenticate_thenExceptionWrapped() throws AuthenticationException, SignatureException{
		TransportAuthenticationToken credentials = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRATION);
		when(authServiceMock.validateToken(credentials)).thenThrow(new SignatureException());
		
		Optional<LoggedInUser> user = candidate.authenticate(credentials);
		
		assertThat(user.isPresent(), is(equalTo(false)));
	}
}
