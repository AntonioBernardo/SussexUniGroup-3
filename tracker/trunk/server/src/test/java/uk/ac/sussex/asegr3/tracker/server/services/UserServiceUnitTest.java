package uk.ac.sussex.asegr3.tracker.server.services;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceUnitTest {

	private static final String TEST_USERNAME = "username";

	private static final String TEST_PASSWORD = "password";

	private static final String TEST_PASSWORD_HASH = null;

	private UserService candidate;
	
	@Mock
	private UserDao userDaoMock;
	
	@Mock
	private AuthenticationService authenticationServiceMock;
	
	@Before
	public void before(){
		candidate = new UserService(userDaoMock, authenticationServiceMock);
		when(userDaoMock.exists(TEST_USERNAME)).thenReturn(false);
		when(authenticationServiceMock.computePwHash(TEST_PASSWORD)).thenReturn(TEST_PASSWORD_HASH);
	}
	
	@Test(expected=UserAlreadyExistsException.class)
	public void givenExisitingUser_whenCallingAddUser_thenThrowException() throws UserAlreadyExistsException{
		when(userDaoMock.exists(TEST_USERNAME)).thenReturn(true);
		candidate.addNewUser(TEST_USERNAME, TEST_PASSWORD);
	}
	
	@Test
	public void givenNewUser_whenCallingAddUser_thenUserAndHashedPwInsertedIntoDao() throws UserAlreadyExistsException{
		
		candidate.addNewUser(TEST_USERNAME, TEST_PASSWORD);
		
		verify(userDaoMock, times(1)).insert(TEST_USERNAME, TEST_PASSWORD_HASH);
	}
}
