package ac.uk.sussex.asegr3.tracker;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.service.NewUserCallback;
import uk.ac.sussex.asegr3.tracker.client.service.NewUserService;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.transport.AuthenticationException;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApiFactory;
import uk.ac.sussex.asegr3.tracker.client.ui.FetchLocationCallBack;
import uk.ac.sussex.asegr3.tracker.server.TrackerService;
import uk.ac.sussex.asegr3.transport.beans.Base64Encoder;

import static uk.ac.sussex.asegr3.tracker.server.IntegrationTestingUtils.startEmbeddedJetty;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TrackerIntegrationTest {

	private static TrackerService candidate;
	private static Server server;
	private static final String TEST_USER_NAME = "testing_user1";
	private static final String TEST_PASSWORD = "testing_pass";
	private static final uk.ac.sussex.asegr3.tracker.client.util.Logger CLIENT_LOGGER = new SLF4JLogger();
	private static final NetworkInfoProvider ALWAYS_ON_NETWORK_PROVIDER = new MockedNetworkInfoProvider();
	private static final Base64Encoder BASE_64_ENCODER = new Base64Encoder(){

			@Override
			public String encode(byte[] bytes) {
				return Base64.encodeBase64String(bytes);
			}
			
		};
		
	private static final Executor SYNCHRONOUS_EXECUTOR = new Executor(){

		@Override
		public void execute(Runnable command) {
			command.run();
		}
		
	};
	private static final String LOCAL_ADDRESS = "localhost:4312";
	private static final String TEST_NEW_USERNAME = "newTestUser";
	private static final String TEST_NEW_USER_PASSWORD = "newTestUserPassword";
	
	
	@BeforeClass
	public static void before() throws Exception{
		candidate = new TrackerService();
		server = startEmbeddedJetty(candidate, null);
	}
	
	@Test
	public void givenCredentials_whenCallinglogin_thenExpectedTokenReturned() throws MalformedURLException, URISyntaxException, AuthenticationException{
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(LOCAL_ADDRESS, CLIENT_LOGGER, ALWAYS_ON_NETWORK_PROVIDER, BASE_64_ENCODER);
		HttpTransportClientApi api = apiFactory.create(TEST_USER_NAME, TEST_PASSWORD);
		
		assertThat(api, is(not(nullValue())));
	}
	
	@Test(expected=AuthenticationException.class)
	public void givenIncorrectCredentials_whenCallinglogin_thenAuthenticationExceptionThrown() throws MalformedURLException, URISyntaxException, AuthenticationException{
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(LOCAL_ADDRESS, CLIENT_LOGGER, ALWAYS_ON_NETWORK_PROVIDER, BASE_64_ENCODER);
		apiFactory.create(TEST_USER_NAME, TEST_PASSWORD+45);

	}
	
	@Test
	public void givenTestBatch_whenCallingprocessBatch_thenBatchAddedToDb() throws MalformedURLException, URISyntaxException, AuthenticationException{
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(LOCAL_ADDRESS, CLIENT_LOGGER, ALWAYS_ON_NETWORK_PROVIDER, BASE_64_ENCODER);
		HttpTransportClientApi api = apiFactory.create(TEST_USER_NAME, TEST_PASSWORD);
		
		List<LocationDto> testLocations = new ArrayList<LocationDto>();
		for (int i = 0; i < 5; i++){
			testLocations.add(new LocationDto(4565+i, 2332-i, 123456+i));
		}
		LocationBatch testBatch = new LocationBatch(testLocations, 1);
		assertThat(api.processBatch(testBatch), is(equalTo(true)));
	}
	
	@Test
	public void givenLocationsToFetch_whenCallingGetLocations_thenCorrectLocationsReturned() throws MalformedURLException, URISyntaxException, AuthenticationException{
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(LOCAL_ADDRESS, CLIENT_LOGGER, ALWAYS_ON_NETWORK_PROVIDER, BASE_64_ENCODER);
		
		final AtomicBoolean processedFecthedLocations = new AtomicBoolean(false);
		LocationService locationService = new LocationService(null, 1, null, apiFactory.create(TEST_USER_NAME, TEST_PASSWORD),SYNCHRONOUS_EXECUTOR, new FetchLocationCallBack() {
			
			@Override
			public void processFetchLocations(List<LocationDto> locations) {
				assertThat(locations, is(not(nullValue())));
				assertThat(locations.size(), is(equalTo(1)));
				processedFecthedLocations.set(true);
			}
			
			@Override
			public void processFetchFailed(Exception e) {
				fail("error from request: "+ e.getMessage());
			}
		});
		locationService.getNearbyLocations();
		
		assertThat(processedFecthedLocations.get(), is(equalTo(true)));
	}
	
	@Test
	public void givenNewUser_whenCallingNewUser_thenNewUserAdded() throws MalformedURLException, URISyntaxException{
		HttpTransportClientApiFactory apiFactory = HttpTransportClientApiFactory.create(LOCAL_ADDRESS, CLIENT_LOGGER, ALWAYS_ON_NETWORK_PROVIDER, BASE_64_ENCODER);
		
		final AtomicBoolean processedUser = new AtomicBoolean(false);
		NewUserService newUserService = new NewUserService(apiFactory, SYNCHRONOUS_EXECUTOR, new NewUserCallback() {
			
			@Override
			public void processNewUser(HttpTransportClientApi api) {
				assertThat(api, is(not(nullValue())));
				processedUser.set(true);
			}
			
			@Override
			public void processFailedSignup(Exception e) {
				e.printStackTrace();
				fail("error from request: "+ e.getMessage());
				
			}
		});
		
		newUserService.signUp(TEST_NEW_USERNAME, TEST_NEW_USER_PASSWORD, TEST_NEW_USER_PASSWORD);
		
		assertThat(processedUser.get(), is(equalTo(true)));
		
	}
}

class SLF4JLogger implements uk.ac.sussex.asegr3.tracker.client.util.Logger{

	private static final Logger LOG = LoggerFactory.getLogger(TrackerIntegrationTest.class);
	@Override
	public void debug(Class<?> className, String message) {
		LOG.debug(className.getName()+" - "+message);
	}

	@Override
	public void warn(Class<?> className, String message) {
		LOG.warn(className.getName()+" - "+message);
	}

	@Override
	public void error(Class<?> className, String message) {
		LOG.error(className.getName()+" - "+message);
	}	
}
class MockedNetworkInfoProvider implements NetworkInfoProvider {

	private android.net.NetworkInfo mockedNetworkInfo;
	
	MockedNetworkInfoProvider(){
		mockedNetworkInfo = mock(android.net.NetworkInfo.class);
		when(mockedNetworkInfo.isAvailable()).thenReturn(true);
	}
	@Override
	public android.net.NetworkInfo getNetworkInfo() {
		return mockedNetworkInfo;
	}
	
}