package uk.ac.sussex.asegr3.tracker.client.ui;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

import android.app.Activity;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocationFactoryUnitTest {

	private LocationFactory candidate;
	
	@Mock
	private Activity activityMock;
	
	@Mock
	private Logger loggerMock;
	
	@Before
	public void before(){
		this.candidate = new LocationFactory();
	}
	
	@Test
	public void givenLocationFactory_whenCallingCreate_newLocationFactoryReturned(){
		LocationService returnedService = candidate.create(activityMock, loggerMock);
		
		assertThat(returnedService, is(not(nullValue())));
	}
}
