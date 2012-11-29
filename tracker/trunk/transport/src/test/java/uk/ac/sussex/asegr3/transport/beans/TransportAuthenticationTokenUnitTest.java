package uk.ac.sussex.asegr3.transport.beans;

import org.apache.commons.codec.binary.Base64;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;

public class TransportAuthenticationTokenUnitTest {

	private static final String TEST_USER = "test_user";
	private static final String TEST_SIGNATURE = "abcdef_456";
	private static final long TEST_EXPIRES = 3454536;
	private static final String TEST_TOKEN = "dGVzdF91c2Vy_3454536_YWJjZGVmXzQ1Ng==";
	private static final String TEST_INVALID_TOKEN = "dGVzdF91c2Vy3454536_YWJjZGVmXzQ1Ng==";
	
	private TransportAuthenticationToken candidate;
	
	@Before
	public void before(){
		candidate = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRES);
	}
	
	@Test
	public void givenTransportAuthenticationToken_whenCallingGetToken_correctValueReturned(){
		String token = candidate.getToken(new Base64Encoder(){

			@Override
			public String encode(byte[] bytes) {
				return Base64.encodeBase64String(bytes);
			}

			@Override
			public byte[] decode(String code) {
				// TODO Auto-generated method stub
				return Base64.decodeBase64(code);
			}
			
		});
		candidate.toString();
		
		assertThat(token, is(equalTo(TEST_TOKEN)));
	}
	
	@Test
	public void givenStringToken_whenCallingcreateAuthenticationTokenFromString_correctValueReturned(){
		TransportAuthenticationToken token = TransportAuthenticationToken.createAuthenticationTokenFromString(TEST_TOKEN);
		
		assertThat(token.getUsername(), is(equalTo(TEST_USER)));
		assertThat(token.getSignature(), is(equalTo(TEST_SIGNATURE)));
		assertThat(token.getExpires(), is(equalTo(TEST_EXPIRES)));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void givenInvalidStringToken_whenCallingCreateAuthenticationTokenFromString_thenExceptionThrown(){
		TransportAuthenticationToken.createAuthenticationTokenFromString(TEST_INVALID_TOKEN);
	}
	
	@Test
	public void givenEqualTokens_whenCallingEquals_thenReturnsTrue(){
		assertThat(candidate, is(equalTo(new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRES))));
	}
	
	@Test
	public void givenNotEqualUserName_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate, is(not(equalTo(new TransportAuthenticationToken(TEST_USER+1, TEST_SIGNATURE, TEST_EXPIRES)))));
	}
	
	@Test
	public void givenNotEqualSignature_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate, is(not(equalTo(new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE+1, TEST_EXPIRES)))));
	}
	
	@Test
	public void givenNotEqualExpiration_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate, is(not(equalTo(new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRES+1)))));
	}
	
	@Test
	public void givenNotSameTypeTokens_whenCallingEquals_thenReturnsFalse(){
		assertThat(candidate, is(not(equalTo(new Object()))));
	}
	
	@Test
	public void givenEqualTokens_whenCallingHashCode_thenReturnsSameValue(){
		int hash1 = new TransportAuthenticationToken(TEST_USER, TEST_SIGNATURE, TEST_EXPIRES).hashCode();
		int hash2 = candidate.hashCode();
		
		assertThat(hash1, is(equalTo(hash2)));
	}
}
