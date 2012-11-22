package uk.ac.sussex.asegr3.tracker.client.transport;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.client.ClientProtocolException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import android.net.NetworkInfo;

import uk.ac.sussex.asegr3.tracker.client.dto.LocationDto;
import uk.ac.sussex.asegr3.tracker.client.location.LocationBatch;
import uk.ac.sussex.asegr3.tracker.client.sytem.NetworkInfoProvider;
import uk.ac.sussex.asegr3.tracker.client.util.Logger;
import uk.ac.sussex.asegr3.transport.beans.Base64Encoder;

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

	private static final String TEST_TOKEN = "dGVzdFVzZXI=_1234567_YWJjZDEyMzQ=";

	private static final String TEST_USERNAME = "testUser";

	private static final String TEST_PASSWORD = "testPassword";

	private static final String EXPECTED_AUTH_REQUEST = "{\"password\":\"testPassword\"}";

	private static final String TEST_SUC_AUTH_RESPONSE = "{\"username\":\"testUser\", \"signature\":\"abcd1234\", \"expires\"=\"1234567\"}";
	
	@Mock
	private Logger loggerMock;
	
	@Mock
	private NetworkInfoProvider networkInfoProviderMock;
	
	@Mock
	private NetworkInfo networkInfoMock;
	
	@Mock
	private HttpClientFactory httpClientFactoryMock;
	
	@Mock
	private HttpURLConnection httpConnectionMock;
	
	
	private ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	
	private ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[]{});
	
	private HttpTransportClient candidate;
	
	@Before
	public void before() throws ClientProtocolException, IOException, URISyntaxException{
		candidate = new HttpTransportClient(TEST_HOSTNAME, loggerMock, networkInfoProviderMock, httpClientFactoryMock, new Base64Encoder(){

			@Override
			public String encode(byte[] bytes) {
				return Base64.encodeBase64String(bytes);
			}
			
		});
		final ArgumentCaptor<URL> requestedUrl = ArgumentCaptor.forClass(URL.class);
		when(httpClientFactoryMock.createHttpConnection(requestedUrl.capture())).thenReturn(httpConnectionMock);
		when(httpConnectionMock.getURL()).thenAnswer(new Answer<URL>(){

			@Override
			public URL answer(InvocationOnMock invocation) throws Throwable {
				return requestedUrl.getValue();
			}
			
		});
		when(httpConnectionMock.getOutputStream()).thenReturn(byteArrayOutputStream);
		when(httpConnectionMock.getInputStream()).thenReturn(byteArrayInputStream);
		
		when(httpConnectionMock.getResponseCode()).thenReturn(200);
		
		when(networkInfoProviderMock.getNetworkInfo()).thenReturn(networkInfoMock);
	}
	
	@Test
	public void givenCorrectLoginCredentials_whenCallingLogin_thenReturnCorrectToken() throws AuthenticationException, IOException{
		byteArrayInputStream = new ByteArrayInputStream(TEST_SUC_AUTH_RESPONSE.getBytes());
		when(httpConnectionMock.getInputStream()).thenReturn(byteArrayInputStream);
		
		String token = candidate.login(TEST_USERNAME, TEST_PASSWORD);
		
		verify(httpClientFactoryMock, times(1)).createHttpConnection(any(URL.class));
		
		String sentJsonContent = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
		
		assertThat(sentJsonContent, is(equalTo(EXPECTED_AUTH_REQUEST)));
		
		assertThat(token, is(equalTo(TEST_TOKEN)));
	}
	
	@Test
	public void givenSingleEntryBatch_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME)), 0);
		assertThat(candidate.processBatch(TEST_TOKEN, singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpConnection(any(URL.class));
		
		String sentJsonContent = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
		
		assertThat(sentJsonContent, is(equalTo(EXPECTED_SINGLE_LOCATION_JSON_CONTENT)));
	}
	
	@Test
	public void givenMultipleEntryBatch_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(TEST_TOKEN, singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpConnection(any(URL.class));
		
		String sentJsonContent = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
		
		assertThat(sentJsonContent, is(equalTo(EXPECTED_MULTIPLE_LOCATION_JSON_CONTENT)));
		
	}
	
	@Test
	public void givenMultipleEntryBatchAnd204StatusCode_whenCallingProcessBatch_httpClientInvokedCorrectly() throws ClientProtocolException, IOException{
		
		when(httpConnectionMock.getResponseCode()).thenReturn(204);
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(TEST_TOKEN, singleBatch), is(equalTo(true)));
		
		verify(httpClientFactoryMock, times(1)).createHttpConnection(any(URL.class));
		
		String sentJsonContent = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
		
		assertThat(sentJsonContent, is(equalTo(EXPECTED_MULTIPLE_LOCATION_JSON_CONTENT)));
	}
	
	@Test
	public void givenMultipleEntryBatchAnd500StatusCode_whenCallingProcessBatch_thenResultIsFalse() throws ClientProtocolException, IOException{
		
		when(httpConnectionMock.getResponseCode()).thenReturn(500);
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		assertThat(candidate.processBatch(TEST_TOKEN, singleBatch), is(equalTo(false)));
	}
	
	@Test
	public void givenMultipleEntryBatchAndIoException_whenCallingProcessBatch_thenResultIsFalse() throws ClientProtocolException, IOException{
		
		LocationBatch singleBatch = new LocationBatch(Arrays.asList(new LocationDto(TEST_LAT, TEST_LONG, TEST_TIME),
																	new LocationDto(TEST_LAT+5, TEST_LONG-6, TEST_TIME+1000)), 0);
		
		when(httpConnectionMock.getInputStream()).thenThrow(new IOException());
		
		assertThat(candidate.processBatch(TEST_TOKEN, singleBatch), is(equalTo(false)));
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
