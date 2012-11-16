package uk.ac.sussex.asegr3.tracker.client.service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.client.service.login.LoginGrantedListener;
import uk.ac.sussex.asegr3.tracker.client.service.login.LoginService;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceUnitTest {

	private static final String TEST_HOSTNAME = "localhost";

	private static final String TEST_USERNAME = "testUser";

	private static final String TEST_PASSWORD = "testPassword";

	private LoginService candidate;
	
	@Mock
	private LoginGrantedListener loginGrantedListenerMock;
	
	@Mock
	private Logger loggerMock;

	@Mock
	private NetworkInfoProvider networkInfoProviderMock;
	
	private HttpTransportClientApiFactory apiFactory;
	
	@Before
	public void before() throws MalformedURLException, URISyntaxException{
		apiFactory = HttpTransportClientApiFactory.create(TEST_HOSTNAME, loggerMock, networkInfoProviderMock);
		this.candidate = new LoginService(loginGrantedListenerMock, new Executor(){

			@Override
			public void execute(Runnable command) {
				command.run(); // sync executor
			}
			
		}, apiFactory, loggerMock);
	}
	
	@Test
	public void givenValidUserNamePasswords_whenCallingLogin_thenListenerNotifiedWithApi(){
		candidate.login(TEST_USERNAME, TEST_PASSWORD);
		
		verify(loginGrantedListenerMock, times(1)).processLogin(any(HttpTransportClientApi.class));
	}
	
	//@Test
	public void givenInValidUserNamePasswords_whenCallingLogin_thenListenerNotifiedWithApi(){
		Exception exception = new RuntimeException("TEST_ERROR");
		when(apiFactory.create(TEST_USERNAME, TEST_PASSWORD)).thenThrow(exception);
		candidate.login(TEST_USERNAME, TEST_PASSWORD);
		
		verify(loginGrantedListenerMock, never()).processLogin(any(HttpTransportClientApi.class));
		verify(loginGrantedListenerMock, times(1)).processFailedLogin(exception);
	}
}
