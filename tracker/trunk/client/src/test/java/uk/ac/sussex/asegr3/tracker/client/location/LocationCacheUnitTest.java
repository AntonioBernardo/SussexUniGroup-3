package uk.ac.sussex.asegr3.tracker.client.location;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.google.common.collect.Iterables;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;
import static org.mockito.Matchers.any;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class LocationCacheUnitTest {

	private static final int CACHE_LIMIT = 200;
	private static final int DEFAULT_BATCH_SIZE = 10;
	private static final long CACHE_FLUSH_TIME = TimeUnit.MINUTES.toMillis(1);
	private static final long TEST_TIME = 0;
	private static final double TEST_LONG = 0;
	private static final double TEST_LAT = 0;
	
	private LocationCache candidate;
	
	@Mock
	private BatchLocationConsumer batchLocationConsumerMock;
	
	@Mock
	private Logger loggerMock;
	
	@Before
	public void before(){
		this.candidate = new LocationCache(CACHE_LIMIT, DEFAULT_BATCH_SIZE, CACHE_FLUSH_TIME, batchLocationConsumerMock, loggerMock, new Executor(){

			@Override
			public void execute(Runnable command) {
				command.run(); // same thread
			}
			
		});
		when(batchLocationConsumerMock.isReady()).thenReturn(true);
		when(batchLocationConsumerMock.processBatch(any(LocationBatch.class))).thenReturn(true);
	}
	
	@Test
	public void givenConsumerAvaliableConsumer_whenCallingNotifyNewLocationOnce_thenNoLocationIsNotified(){
		LocationDto LocationDtoMock = mock(LocationDto.class);
		candidate.notifyNewLocation(LocationDtoMock);
		
		verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
	}
	
	@Test
	public void givenConsumerAvailable_whenCallingNotifyNewLocationTenTimes_thenLocationIsNotifiedOnce(){
		for (int i = 0; i < DEFAULT_BATCH_SIZE; i++){
			verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
			LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+i);
			candidate.notifyNewLocation(LocationDtoMock);
		}
		verify(batchLocationConsumerMock, times(1)).processBatch(any(LocationBatch.class));
	}
	
	@Test
	public void givenConsumerAvailable_whenCallingNotifyNewLocationFiftyTimes_thenLocationIsNotifiedFiveTimes(){
		for (int batchNum = 0; batchNum < 5; batchNum++){
			for (int i = 0; i < DEFAULT_BATCH_SIZE; i++){
				verify(batchLocationConsumerMock, times(batchNum)).processBatch(any(LocationBatch.class));
				LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+i);
				candidate.notifyNewLocation(LocationDtoMock);
			}
			verify(batchLocationConsumerMock, times(batchNum+1)).processBatch(any(LocationBatch.class));
		}
	}
	
	@Test
	public void givenConsumerUnAvailable_whenCallingNotifyNewLocationFiftyTimes_thenLocationIsNotNotified(){
		when(batchLocationConsumerMock.isReady()).thenReturn(false);
		for (int batchNum = 0; batchNum < 5; batchNum++){
			for (int i = 0; i < DEFAULT_BATCH_SIZE; i++){
				verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
				LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+i);
				candidate.notifyNewLocation(LocationDtoMock);
			}
			verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
		}
	}
	
	@Test
	public void givenConsumerUnAvailable_whenCallingNotifyNewLocationThousandAndFiveTimes_thenLocationsAreTrimmed(){
		when(batchLocationConsumerMock.isReady()).thenReturn(false);
		for (int i = 0; i < CACHE_LIMIT+5; i++){
			verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
			LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+i);
			candidate.notifyNewLocation(LocationDtoMock);
			assertThat(candidate.getCacheSize() <= CACHE_LIMIT, is(equalTo(true)));
		}
		verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
		
		// 1005 entries have been added at this point, Then set the consumer to being ready. We should only have 1000 returned
		
		when(batchLocationConsumerMock.isReady()).thenReturn(true);
		
		LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+CACHE_LIMIT+6);
		candidate.notifyNewLocation(LocationDtoMock); // will trigger the callback
		ArgumentCaptor<LocationBatch> captor = ArgumentCaptor.forClass(LocationBatch.class);
		verify(batchLocationConsumerMock, times(1)).processBatch(captor.capture());
		
		assertThat(Iterables.size(captor.getValue().getLocations()), is(equalTo(CACHE_LIMIT)));
		assertThat(candidate.getCacheSize(), is(equalTo(0)));
		
	}
	
	@Test
	public void givenConsumerAvailable_whenCallingNotifyNewLocationThousandAndFiveTimes_thenLocationsAreTrimmed(){
		when(batchLocationConsumerMock.isReady()).thenReturn(false);
		for (int i = 0; i < CACHE_LIMIT+5; i++){
			verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
			LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+i);
			candidate.notifyNewLocation(LocationDtoMock);
		}
		verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
		
		// 1005 entries have been added at this point, Then set the consumer to being ready. We should only have 1000 returned
		
		when(batchLocationConsumerMock.isReady()).thenReturn(true);
		ArgumentCaptor<LocationBatch> capture = ArgumentCaptor.forClass(LocationBatch.class);
		LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME+CACHE_LIMIT+6);
		candidate.notifyNewLocation(LocationDtoMock); // will trigger the callback
		
		verify(batchLocationConsumerMock, times(1)).processBatch(capture.capture());
		
		LocationBatch capturedBatch = capture.getValue();
		
		assertThat(Iterables.size(capturedBatch.getLocations()), is(equalTo(CACHE_LIMIT)));
		assertThat(candidate.getCacheSize(), is(equalTo(0)));
	}
	
	@Test
	public void givenConsumerAvailableAndLocationsWithTimesGreaterTheLimit_whenCallingNotifyNewLocation_thenLocationsAreNotified(){
		long currentTime = System.currentTimeMillis();
		
		LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, currentTime);
		candidate.notifyNewLocation(LocationDtoMock);
		
		verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
		
		LocationDto nextDto = new LocationDto(TEST_LAT, TEST_LONG, currentTime+CACHE_FLUSH_TIME);
		candidate.notifyNewLocation(nextDto);
		verify(batchLocationConsumerMock, times(1)).processBatch(any(LocationBatch.class));
		assertThat(candidate.getCacheSize(), is(equalTo(0)));
	}
	
	@Test
	public void givenFailedConsumer_whenCallingNotifyNewLocation_thenLocationsAreAddedBackIntoQueue(){
		when(batchLocationConsumerMock.processBatch(any(LocationBatch.class))).thenReturn(false);
		long currentTime = System.currentTimeMillis();
		
		LocationDto LocationDtoMock = new LocationDto(TEST_LAT, TEST_LONG, currentTime);
		candidate.notifyNewLocation(LocationDtoMock);
		
		verify(batchLocationConsumerMock, never()).processBatch(any(LocationBatch.class));
		
		LocationDto nextDto = new LocationDto(TEST_LAT, TEST_LONG, currentTime+CACHE_FLUSH_TIME);
		candidate.notifyNewLocation(nextDto);
		verify(batchLocationConsumerMock, times(1)).processBatch(any(LocationBatch.class));
		
		assertThat(candidate.getCacheSize(), is(equalTo(2)));
	}
}
