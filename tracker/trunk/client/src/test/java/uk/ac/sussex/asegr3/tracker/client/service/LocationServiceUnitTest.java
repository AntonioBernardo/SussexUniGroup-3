package uk.ac.sussex.asegr3.tracker.client.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.service.LocationService;
import uk.ac.sussex.asegr3.tracker.client.service.LocationUpdateListener;

import android.location.Location;
import android.location.LocationManager;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class LocationServiceUnitTest {

	private static final double TEST_LOCATION_LAT = 53;

	private static final double TEST_LOCATION_LONG = 2;

	private static final long TEST_LOCATION_TIME = 56445674;

	private static final Float GOOD_ACCURACY = 10f;

	private static final int TEST_PROXIMITY_DISTANCE = 10;

	private LocationService candidate;
	
	@Mock
	private LocationManager locationManagerMock;
	
	@Mock
	private LocationUpdateListener locationUpdateListenerMock;
	
	@Before
	public void before(){
		candidate = new LocationService(locationManagerMock, TEST_PROXIMITY_DISTANCE);
		candidate.registerListener(locationUpdateListenerMock);
	}
	
	
	
	//@Test
	public void ReceivingLocation_from_LocationManager(){
		
		//it will compare that the location is actually receiving something form the manager by checking that is not null
		Location androidLocationMock = mock(Location.class);
		when(androidLocationMock.getLatitude()).thenReturn(TEST_LOCATION_LAT);
		when(androidLocationMock.getLongitude()).thenReturn(TEST_LOCATION_LONG);
		when(androidLocationMock.getTime()).thenReturn(TEST_LOCATION_TIME);
		when(androidLocationMock.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		//when(locationManagerMock.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(androidLocationMock);
		
		candidate.onLocationChanged(androidLocationMock);
		LocationDto expectedLocationDto = new LocationDto(TEST_LOCATION_LAT,TEST_LOCATION_LONG,TEST_LOCATION_TIME);
		
		//want to verify if actually  fails
		
		verify(locationUpdateListenerMock).notifyNewLocation(expectedLocationDto);
		
		
	}
	
	//@Test
	public void FowardingLocation_to_stack(){
		
		
		Location androidLocationMock = mock(Location.class);
		when(androidLocationMock.getLatitude()).thenReturn(TEST_LOCATION_LAT);
		when(androidLocationMock.getLongitude()).thenReturn(TEST_LOCATION_LONG);
		when(androidLocationMock.getTime()).thenReturn(TEST_LOCATION_TIME);
		when(androidLocationMock.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		candidate.onLocationChanged(androidLocationMock);
		LocationDto expectedLocationDto = new LocationDto(TEST_LOCATION_LAT, TEST_LOCATION_LONG, TEST_LOCATION_TIME);
		
		
	}
	
	
	
	public void Recieving_BadAccuracy(){
	
	}
	
	@Test
	public void givenValidLocation_whenCallingDoLocationFiltering_thenListenerNotified(){
		
		Location androidLocationMock = mock(Location.class);
		when(androidLocationMock.getLatitude()).thenReturn(TEST_LOCATION_LAT);
		when(androidLocationMock.getLongitude()).thenReturn(TEST_LOCATION_LONG);
		when(androidLocationMock.getTime()).thenReturn(TEST_LOCATION_TIME);
		when(androidLocationMock.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		candidate.onLocationChanged(androidLocationMock);
		LocationDto expectedLocationDto = new LocationDto(TEST_LOCATION_LAT, TEST_LOCATION_LONG, TEST_LOCATION_TIME);
		
		
		verify(locationUpdateListenerMock, times(1)).notifyNewLocation(expectedLocationDto);
	
	}
	
	@Test
	public void given2ValidLocationsInProximity_whenCallingDoLocationFiltering_thenNoListenerNotified(){
		
		Location androidLocationMock = mock(Location.class);
		when(androidLocationMock.getLatitude()).thenReturn(TEST_LOCATION_LAT);
		when(androidLocationMock.getLongitude()).thenReturn(TEST_LOCATION_LONG);
		when(androidLocationMock.getTime()).thenReturn(TEST_LOCATION_TIME);
		when(androidLocationMock.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		when(locationManagerMock.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(androidLocationMock);
		
		Location nearLocation = mock(Location.class);
		when(nearLocation.getLatitude()).thenReturn(TEST_LOCATION_LAT+3);
		when(nearLocation.getLongitude()).thenReturn(TEST_LOCATION_LONG+4);
		when(nearLocation.getTime()).thenReturn(TEST_LOCATION_TIME+1000);
		when(nearLocation.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		candidate.onLocationChanged(nearLocation);
		
		verify(locationUpdateListenerMock, never()).notifyNewLocation(any(LocationDto.class)); // still only called once.
	
	}
	
	@Test
	public void given2ValidLocationsNotInProximity_whenCallingDoLocationFiltering_thenListenerNotified(){
		
		Location androidLocationMock = mock(Location.class);
		when(androidLocationMock.getLatitude()).thenReturn(TEST_LOCATION_LAT);
		when(androidLocationMock.getLongitude()).thenReturn(TEST_LOCATION_LONG);
		when(androidLocationMock.getTime()).thenReturn(TEST_LOCATION_TIME);
		when(androidLocationMock.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		when(locationManagerMock.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(androidLocationMock);
		
		Location nearLocation = mock(Location.class);
		when(nearLocation.getLatitude()).thenReturn(TEST_LOCATION_LAT+3);
		when(nearLocation.getLongitude()).thenReturn(TEST_LOCATION_LONG+TEST_PROXIMITY_DISTANCE);
		when(nearLocation.getTime()).thenReturn(TEST_LOCATION_TIME+1000);
		when(nearLocation.getAccuracy()).thenReturn(GOOD_ACCURACY);
		
		when(androidLocationMock.distanceTo(nearLocation)).thenReturn(TEST_PROXIMITY_DISTANCE+1f);
		
		candidate.onLocationChanged(nearLocation);
		
		LocationDto expectedLocationDto = new LocationDto(TEST_LOCATION_LAT+3, TEST_LOCATION_LONG+TEST_PROXIMITY_DISTANCE, TEST_LOCATION_TIME+1000);
		ArgumentCaptor<LocationDto> capture = ArgumentCaptor.forClass(LocationDto.class);
		verify(locationUpdateListenerMock, times(1)).notifyNewLocation(capture.capture()); // still only called once.
		
		assertThat(capture.getValue().equals(expectedLocationDto), is(equalTo(true)));
	}
	
	
	
	
	
	
}
