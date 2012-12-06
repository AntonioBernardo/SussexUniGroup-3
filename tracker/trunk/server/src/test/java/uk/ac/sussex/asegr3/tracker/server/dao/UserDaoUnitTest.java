package uk.ac.sussex.asegr3.tracker.server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.DBI;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoUnitTest {

	private static final String EXPECTED_COUNT_SQL = "select count(1) > 0 from user where username=?";
	private static final String EXPECTED_INSERT_SQL = "insert into user(username, password, name, surname, age, gender, about, interests, lastloggindate, signupdate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String TEST_PW_HASH = "testpwHash";
	private static final String TEST_EMAIL_ADDRESS = "testing@testing.com";
	private static final String TEST_NAME = "TestingName";
	private static final String TEST_SURNAME = "TestingSurname";
	private static final int TEST_AGE = 21;
	private static final UserDTO.Gender TEST_GENDER = UserDTO.Gender.MALE;
	private static final String TEST_ABOUT = "testingAbout";
	private static final String TEST_INTERESTS = "testingInterests";
	private static final long TEST_LAST_LOGGIN_DATE = System.currentTimeMillis();
	private static final long TEST_SIGN_UP = System.currentTimeMillis();
	
	public static final UserDTO TEST_USER = new UserDTO(TEST_EMAIL_ADDRESS, TEST_NAME, TEST_SURNAME, TEST_AGE, TEST_GENDER, TEST_ABOUT, TEST_INTERESTS, TEST_LAST_LOGGIN_DATE, TEST_SIGN_UP);

	private UserDao candidate;
	
	@Mock
	private DataSource datasourceMock;
	
	@Mock
	private Connection connectionMock;
	
	private StatementsConnectionCaptor connectionCaptor;
	
	@Mock
	private Statement statementMock;
	
	@Mock
	private PreparedStatement preparedStatementMock;
	
	@Mock
	private ResultSet resultSetMock;
	
	@Before
	public void before() throws SQLException{
		DBI dbi = new DBI(datasourceMock);
		this.connectionCaptor = new StatementsConnectionCaptor(connectionMock) ;
		
		when(datasourceMock.getConnection()).thenReturn(connectionCaptor);
		when(connectionMock.createStatement()).thenReturn(statementMock);
		when(statementMock.getResultSet()).thenReturn(resultSetMock);
		when(resultSetMock.next()).thenReturn(true).thenReturn(false); // 1 row by default
		when(resultSetMock.getBoolean(1)).thenReturn(true).thenReturn(false);
		when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
		when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
		
		this.candidate = dbi.onDemand(UserDao.class);
	}
	
	@Test
	public void givenFoundUser_whenExists_thenCorrectSqlGenerated() throws SQLException{
		assertThat(candidate.exists(TEST_EMAIL_ADDRESS), is(equalTo(true)));
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_COUNT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_EMAIL_ADDRESS);
	}
	
	@Test
	public void givenNonFoundUser_whenExists_thenCorrectSqlGenerated() throws SQLException{
		when(resultSetMock.next()).thenReturn(false);
		assertThat(candidate.exists(TEST_EMAIL_ADDRESS), is(equalTo(false)));
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_COUNT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_EMAIL_ADDRESS);
	}
	
	@Test
	public void givenNewUser_whenCallingInsert_thenCorrectSqlGenerated() throws SQLException{
		candidate.insert(TEST_USER, TEST_PW_HASH);
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_INSERT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_EMAIL_ADDRESS);
		verify(preparedStatementMock, times(1)).setObject(2, TEST_PW_HASH);
		verify(preparedStatementMock, times(1)).setObject(3, TEST_NAME);
		verify(preparedStatementMock, times(1)).setObject(4, TEST_SURNAME);
		verify(preparedStatementMock, times(1)).setObject(5, TEST_AGE);
		verify(preparedStatementMock, times(1)).setObject(6, UserDTO.Gender.getIdFromValue(TEST_GENDER));
		verify(preparedStatementMock, times(1)).setObject(7, TEST_ABOUT);
		verify(preparedStatementMock, times(1)).setObject(8, TEST_INTERESTS);
		verify(preparedStatementMock, times(1)).setObject(9, TEST_LAST_LOGGIN_DATE);
		verify(preparedStatementMock, times(1)).setObject(10, TEST_SIGN_UP);
		
	}
}
