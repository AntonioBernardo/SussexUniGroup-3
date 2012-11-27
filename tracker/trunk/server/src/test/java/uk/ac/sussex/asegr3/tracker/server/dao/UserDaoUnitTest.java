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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoUnitTest {

	private static final String TEST_USERNAME = "testUser";

	private static final String EXPECTED_COUNT_SQL = "select count(1) > 0 from user where username=?";
	private static final String EXPECTED_INSERT_SQL = "insert into user(username, password) values (?, ?)";

	private static final String TEST_PW_HASH = "testpwHash";

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
		assertThat(candidate.exists(TEST_USERNAME), is(equalTo(true)));
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_COUNT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_USERNAME);
	}
	
	@Test
	public void givenNonFoundUser_whenExists_thenCorrectSqlGenerated() throws SQLException{
		when(resultSetMock.next()).thenReturn(false);
		assertThat(candidate.exists(TEST_USERNAME), is(equalTo(false)));
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_COUNT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_USERNAME);
	}
	
	@Test
	public void givenNewUser_whenCallingInsert_thenCorrectSqlGenerated() throws SQLException{
		candidate.insert(TEST_USERNAME, TEST_PW_HASH);
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_INSERT_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_USERNAME);
		verify(preparedStatementMock, times(1)).setObject(2, TEST_PW_HASH);
	}
}
