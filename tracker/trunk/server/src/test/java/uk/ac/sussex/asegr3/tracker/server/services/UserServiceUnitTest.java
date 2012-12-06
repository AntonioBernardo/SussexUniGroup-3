package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.Clock;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

	private static final String TEST_PASSWORD = "password";
	private static final String TEST_EMAIL_ADDRESS = "testing@testing.com";
	private static final String TEST_NAME = "TestingName";
	private static final String TEST_SURNAME = "TestingSurname";
	private static final int TEST_AGE = 21;
	private static final UserDTO.Gender TEST_GENDER = UserDTO.Gender.MALE;
	private static final String TEST_ABOUT = "testingAbout";
	private static final String TEST_INTERESTS = "testingInterests";

	private static final String TEST_PASSWORD_HASH = "testPwHash";
	private static final long TEST_TIME = System.currentTimeMillis();

	private UserService candidate;
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private AuthenticationService authenticationServiceMock;
	
	@Mock
	private Clock clockMock;
	
	@Before
	public void before(){
		
		when(clockMock.getCurrentTime()).thenReturn(TEST_TIME);
		candidate = new UserService(userDaoMock, authenticationServiceMock, clockMock);
		when(userDaoMock.exists(TEST_EMAIL_ADDRESS)).thenReturn(false);
		when(authenticationServiceMock.computePwHash(TEST_PASSWORD)).thenReturn(TEST_PASSWORD_HASH);
	}
	
	@Test(expected=UserAlreadyExistsException.class)
	public void givenExisitingUser_whenCallingAddUser_thenThrowException() throws UserAlreadyExistsException{
		when(userDaoMock.exists(TEST_EMAIL_ADDRESS)).thenReturn(true);
		candidate.addNewUser(TEST_EMAIL_ADDRESS, TEST_PASSWORD, TEST_NAME, TEST_SURNAME, TEST_AGE, UserDTO.Gender.MALE.name(), TEST_ABOUT, TEST_INTERESTS);
	}
	
	@Test
	public void givenNewUser_whenCallingAddUser_thenUserAndHashedPwInsertedIntoDao() throws UserAlreadyExistsException{
		
		candidate.addNewUser(TEST_EMAIL_ADDRESS, TEST_PASSWORD, TEST_NAME, TEST_SURNAME, TEST_AGE, TEST_GENDER.name(), TEST_ABOUT, TEST_INTERESTS);
		
		UserDTO expectedUser = new UserDTO(TEST_EMAIL_ADDRESS, TEST_NAME, TEST_SURNAME, TEST_AGE, TEST_GENDER, TEST_ABOUT, TEST_INTERESTS, TEST_TIME, TEST_TIME);
		
		verify(userDaoMock, times(1)).insert(expectedUser, TEST_PASSWORD_HASH);
	}
}
