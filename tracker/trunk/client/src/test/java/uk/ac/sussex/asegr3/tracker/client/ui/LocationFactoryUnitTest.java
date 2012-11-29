package uk.ac.sussex.asegr3.tracker.client.ui;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

import java.util.concurrent.Executor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.transport.HttpTransportClientApi;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import android.app.Activity;
import android.content.Context;
import android.location.LocationManager;

@RunWith(MockitoJUnitRunner.class)
public class LocationFactoryUnitTest {

	private LocationServiceFactory candidate;
	
	@Mock
	private Activity activityMock;
	
	@Mock
	private Logger loggerMock;
	
	@Mock
	private MapViewProvider mapViewProviderMock;
	
	@Mock
	private LocationManager locationManagerMock;
	
	@Mock
	private Executor executorMock;
	
	@Mock
	private HttpTransportClientApi apiMock;
	
	@Mock
	private FetchLocationCallBack fetchLocationCallBackMock;

	
	@Before
	public void before(){
		this.candidate = new LocationServiceFactory();
		when(activityMock.getSystemService(Context.LOCATION_SERVICE)).thenReturn(locationManagerMock);
	}
	
	@Test
	public void givenLocationFactory_whenCallingCreate_newLocationFactoryReturned(){
		LocationService returnedService = candidate.create(activityMock, apiMock, mapViewProviderMock, loggerMock, executorMock,
				fetchLocationCallBackMock);
		
		assertThat(returnedService, is(not(nullValue())));
	}
}
