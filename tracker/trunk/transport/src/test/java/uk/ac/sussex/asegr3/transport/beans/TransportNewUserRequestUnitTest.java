package uk.ac.sussex.asegr3.transport.beans;

import org.junit.Before;
import org.junit.Test;

import uk.ac.sussex.asegr3.transport.beans.AbstractTransportUserRequest.Gender;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TransportNewUserRequestUnitTest {

	private static final String TEST_USERNAME = "testUser@testing.com";
	private static final String TEST_PASSWORD = "testPassword";
	private static final String TEST_NAME = "TestingName";
	private static final String TEST_SURNAME = "TestingSurname";
	private static final int TEST_AGE = 21;
	private static final Gender TEST_GENDER = Gender.MALE;
	private static final String TEST_ABOUT = "testingAbout";
	private static final String TEST_INTERESTS = "testingInterests";
	
	private TransportNewUserRequest candidate;
	
	@Before
	public void before() throws InvalidEmailException{
		this.candidate = new TransportNewUserRequest(TEST_USERNAME, TEST_PASSWORD, TEST_NAME, TEST_SURNAME, TEST_AGE, TEST_GENDER, TEST_ABOUT, TEST_INTERESTS);
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetUserName_thenCorrectStringReturned(){
		assertThat(candidate.getEmail(), is(equalTo(TEST_USERNAME)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetPassword_thenCorrectStringReturned(){
		assertThat(candidate.getPassword(), is(equalTo(TEST_PASSWORD)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetName_thenCorrectStringReturned(){
		assertThat(candidate.getName(), is(equalTo(TEST_NAME)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetSurname_thenCorrectStringReturned(){
		assertThat(candidate.getSurname(), is(equalTo(TEST_SURNAME)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetAge_thenCorrectStringReturned(){
		assertThat(candidate.getAge(), is(equalTo(TEST_AGE)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetGender_thenCorrectStringReturned(){
		assertThat(candidate.getGender(), is(equalTo(TEST_GENDER)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetAboutYou_thenCorrectStringReturned(){
		assertThat(candidate.getAboutYou(), is(equalTo(TEST_ABOUT)));
	}
	
	@Test
	public void givenNewTransportNewUserRequest_whenCallingGetInterests_thenCorrectStringReturned(){
		assertThat(candidate.getInterests(), is(equalTo(TEST_INTERESTS)));
	}
	
	@Test(expected=InvalidEmailException.class)
	public void givenIllegalEmailAddress_whenCallingConstructor_thenExceptionThrown() throws InvalidEmailException{
		new TransportNewUserRequest("dfgdfgd", TEST_PASSWORD, TEST_NAME, TEST_SURNAME, TEST_AGE, TEST_GENDER, TEST_ABOUT, TEST_INTERESTS);
	}
}
