package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import android.net.NetworkInfo;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Matchers.any;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class HttpTransportClientUnitTest {

	private static final String TEST_HOSTNAME = "localhost";

	private static final double TEST_LAT = 45.657;

	private static final double TEST_LONG = 85.623;

	private static final long TEST_TIME = 1351874788222L;

	private static final String EXPECTED_SINGLE_LOCATION_JSON_CONTENT = "{\"locations\":[{\"timestamp\":1351874788222,\"lattitude\":45.657,\"longitude\":85.623}]}";

	private static final Object EXPECTED_MULTIPLE_LOCATION_JSON_CONTENT = "{\"locations\":[{\"timestamp\":1351874788222,\"lattitude\":45.657,\"longitude\":85.623},{\"timestamp\":1351874789222,\"lattitude\":50.657,\"longitude\":79.623}]}";
	
	@Mock
	private Logger loggerMock;
	
	@Mock
	private NetworkInfoProvider networkInfoProviderMock;
	
	@Mock
	private NetworkInfo networkInfoMock;
	
	@Mock
	private HttpClientFactory httpClientFactoryMock;
	
	@Mock
	private HttpClient httpClientMock;
	
	@Mock
	private HttpResponse httpResponseMock;
	
	@Mock
	private StatusLine statusLineMock;
	
	private HttpTransportClient candidate;
	
	@Before
	public void before() throws ClientProtocolException, IOException{
		candidate = new HttpTransportClient(TEST_HOSTNAME, loggerMock, networkInfoProviderMock, httpClientFactoryMock);
		when(httpClientFactoryMock.createHttpClient()).thenReturn(httpClientMock);
		when(httpClientMock.execute(any(HttpPost.class))).thenReturn(httpResponseMock);
		
		when(httpResponseMock.getStatusLine()).thenReturn(statusLineMock);
		when(statusLineMock.getStatusCode()).thenReturn(200);
		
		when(networkInfoProviderMock.getNetworkInfo()).thenReturn(networkInfoMock);
	}
	
	@Test
	public void givenSingleEntryBatch_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME)), 0);
		assertThat(candidate.processBatch(singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpClient();
		ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
		verify(httpClientMock, times(1)).execute(captor.capture());
		
		HttpPost capturedPost = captor.getValue();
		
		assertThat(capturedPost.getHeaders("Content-type")[0].getValue(), is(equalTo("application/json")));
		assertThat(capturedPost.getHeaders("Accept")[0].getValue(), is(equalTo("application/json")));
		
		HttpEntity entity = capturedPost.getEntity();
		
		String jsonContent = new String(IOUtils.toByteArray(entity.getContent()));
		
		assertThat(jsonContent, is(equalTo(EXPECTED_SINGLE_LOCATION_JSON_CONTENT)));
	}
	
	@Test
	public void givenMultipleEntryBatch_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpClient();
		ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
		verify(httpClientMock, times(1)).execute(captor.capture());
		
		HttpPost capturedPost = captor.getValue();
		
		assertThat(capturedPost.getHeaders("Content-type")[0].getValue(), is(equalTo("application/json")));
		assertThat(capturedPost.getHeaders("Accept")[0].getValue(), is(equalTo("application/json")));
		
		HttpEntity entity = capturedPost.getEntity();
		
		String jsonContent = new String(IOUtils.toByteArray(entity.getContent()));
		
		assertThat(jsonContent, is(equalTo(EXPECTED_MULTIPLE_LOCATION_JSON_CONTENT)));
	}
	
	@Test
	public void givenMultipleEntryBatchAnd204StatusCode_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		when(statusLineMock.getStatusCode()).thenReturn(204);
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpClient();
		ArgumentCaptor<HttpPost> captor = ArgumentCaptor.forClass(HttpPost.class);
		verify(httpClientMock, times(1)).execute(captor.capture());
		
		HttpPost capturedPost = captor.getValue();
		
		assertThat(capturedPost.getHeaders("Content-type")[0].getValue(), is(equalTo("application/json")));
		assertThat(capturedPost.getHeaders("Accept")[0].getValue(), is(equalTo("application/json")));
		
		HttpEntity entity = capturedPost.getEntity();
		
		String jsonContent = new String(IOUtils.toByteArray(entity.getContent()));
		
		assertThat(jsonContent, is(equalTo(EXPECTED_MULTIPLE_LOCATION_JSON_CONTENT)));
	}
	
	@Test
	public void givenMultipleEntryBatchAnd500StatusCode_whenCallingProcessBatch_thenResultIsFalse() throws ClientProtocolException, IOException{
		
		when(statusLineMock.getStatusCode()).thenReturn(500);
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(singleBatch), is(equalTo(false)));
	}
	
	@Test
	public void givenMultipleEntryBatchAndIoException_whenCallingProcessBatch_thenResultIsFalse() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		when(httpClientMock.execute(any(HttpPost.class))).thenThrow(new IOException());
		
		assertThat(candidate.processBatch(singleBatch), is(equalTo(false)));
	}
	
	@Test
	public void givenMultipleEntryBatchAndClientProtocolException_whenCallingProcessBatch_thenResultIsFalse() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		when(httpClientMock.execute(any(HttpPost.class))).thenThrow(new ClientProtocolException());
		
		assertThat(candidate.processBatch(singleBatch), is(equalTo(false)));
	}
	
	@Test
	public void givenNetworkUnavialable_whenCallingIsReady_thenReturnsFalse() throws ClientProtocolException, IOException{
		when(networkInfoMock.isAvailable()).thenReturn(false);
		
		assertThat(candidate.isReady(), is(equalTo(false)));
	}
	
	@Test
	public void givenNetworkAvailable_whenCallingIsReady_thenReturnsTrue() throws ClientProtocolException, IOException{
		when(networkInfoMock.isAvailable()).thenReturn(true);
		
		assertThat(candidate.isReady(), is(equalTo(true)));
	}
}