package uk.ac.sussex.asegr3.prototype;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.junit.Assert.assertThat;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

import android.location.LocationManager;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceUnitTest {

	private LocationService candidate;
	
	@Mock
	private LocationManager locationManagerMock;
	
	@Before
	public void before(){
		
		candidate = new LocationService(locationManagerMock);
		when(locationManagerMock.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(true);
		when(locationManagerMock.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(true);
	}
	
	@Test
	public void givenNoGpsLocationService_whenCallingHasRequiredPermissions_thenReturnFalse(){
		when(locationManagerMock.isProviderEnabled(LocationManager.GPS_PROVIDER)).thenReturn(false);
		
		assertThat(candidate.hasRequiredPermissions(), is(equalTo(false)));
	}
	
	@Test
	public void givenNoNetworkLocationService_whenCallingHasRequiredPermissions_thenReturnFalse(){
		when(locationManagerMock.isProviderEnabled(LocationManager.NETWORK_PROVIDER)).thenReturn(false);
		
		assertThat(candidate.hasRequiredPermissions(), is(equalTo(false)));
	}
	
	@Test
	public void givenFullyPermissionedLocationService_whenCallingHasRequiredPermissions_thenReturnFalse(){
		
		assertThat(candidate.hasRequiredPermissions(), is(equalTo(true)));
	}
}
