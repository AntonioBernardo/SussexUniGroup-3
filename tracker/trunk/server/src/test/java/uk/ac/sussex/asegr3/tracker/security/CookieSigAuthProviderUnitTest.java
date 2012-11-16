package uk.ac.sussex.asegr3.tracker.security;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.security.CookieSigAuthProvider.CookieSigAuthInjectable;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.core.HttpRequestContext;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(MockitoJUnitRunner.class)
public class CookieSigAuthProviderUnitTest {

	private static final String TEST_REALM = "testRealm";

	private static final long TEST_EXPIRES = 0;

	private static final String TEST_SIG = "testSig";

	private static final String TEST_USERNAME = "testUser";

	private static final String TEST_RESULT = "okResult";

	private CookieSigAuthProvider<String> candidate;
	
	@Mock
	private Authenticator<TransportAuthenticationToken, String> authenticatorMock;
	
	private TransportAuthenticationToken token = new TransportAuthenticationToken(TEST_USERNAME, TEST_SIG, TEST_EXPIRES);

	@Mock
	private Auth authMock;

	@Mock
	private HttpContext httpContextMock;

	@Mock
	private HttpRequestContext httpRequestMock;

	@Mock
	private Cookie cookieMock;
	
	@Before
	public void before() throws AuthenticationException{
		when(authMock.required()).thenReturn(true);
		
		// set up http context
		
		when(cookieMock.getName()).thenReturn(CookieSigAuthProvider.AUTHENTICATION_SIGNATURE_COOKIE_NAME);
		when(cookieMock.getValue()).thenReturn(token.getToken());
		
		Map<String, Cookie> cookieMap = new HashMap<String, Cookie>();
		cookieMap.put(CookieSigAuthProvider.AUTHENTICATION_SIGNATURE_COOKIE_NAME, cookieMock);
		when(httpRequestMock.getCookies()).thenReturn(cookieMap);
		when(httpContextMock.getRequest()).thenReturn(httpRequestMock);
		
		when(authenticatorMock.authenticate(token)).thenReturn(Optional.of(TEST_RESULT));
		this.candidate = new CookieSigAuthProvider<String>(authenticatorMock, TEST_REALM);
	}
	
	@Test
	public void givenValidCookie_whenCallingGetValueOnInjectable_thenTrueReturned(){
		AbstractHttpContextInjectable<String> injectable = (AbstractHttpContextInjectable<String>)candidate.getInjectable(null, authMock, null);
		
		String result = injectable.getValue(httpContextMock);
		
		assertThat(result, is(equalTo(TEST_RESULT)));
	}
	
	@Test(expected=WebApplicationException.class)
	public void givenAbsentCookie_whenCallingGetValueOnInjectable_thenThrowException(){
		when(httpRequestMock.getCookies()).thenReturn(Collections.<String, Cookie>emptyMap());
		AbstractHttpContextInjectable<String> injectable = (AbstractHttpContextInjectable<String>)candidate.getInjectable(null, authMock, null);
		
		injectable.getValue(httpContextMock);
	}
	
	@Test
	public void givenAbsentCookieAndNotRequireAuth_whenCallingGetValueOnInjectable_thenReturnsNull(){
		when(authMock.required()).thenReturn(false);
		when(httpRequestMock.getCookies()).thenReturn(Collections.<String, Cookie>emptyMap());
		CookieSigAuthInjectable<String> injectable = (CookieSigAuthInjectable<String>)candidate.getInjectable(null, authMock, null);
		
		String result = injectable.getValue(httpContextMock);
		
		assertThat(result, is(nullValue()));
	}
	
	@Test(expected=WebApplicationException.class)
	public void givenInvalidToken_whenCallingGetValueOnInjectable_thenThrowsException() throws AuthenticationException{
		
		when(authenticatorMock.authenticate(token)).thenReturn(Optional.<String>absent());
		AbstractHttpContextInjectable<String> injectable = (AbstractHttpContextInjectable<String>)candidate.getInjectable(null, authMock, null);
		
		String result = injectable.getValue(httpContextMock);
		
		assertThat(result, is(nullValue()));
	}
	
	@Test(expected=WebApplicationException.class)
	public void givenExceptionThrownInAuthenticating_whenCallingGetValueOnInjectable_thenThrowsException() throws AuthenticationException{
		
		when(authenticatorMock.authenticate(token)).thenThrow(new AuthenticationException("TEST_ERROR"));
		AbstractHttpContextInjectable<String> injectable = (AbstractHttpContextInjectable<String>)candidate.getInjectable(null, authMock, null);
		
		String result = injectable.getValue(httpContextMock);
		
		assertThat(result, is(nullValue()));
	}
}
