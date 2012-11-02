package uk.ac.sussex.asegr3.tracker.server.dao;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.any;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp.DelegatingConnection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.skife.jdbi.v2.DBI;

@RunWith(MockitoJUnitRunner.class)
public class TrackerDaoUnitTest {
	
	private static final int TEST_USER = 56;
	private static final double TEST_LAT = 85.3453;
	private static final double TEST_LONG = 98453.5646432;
	private static final long TEST_TIMESTAMP = 34578345723L;
	private static final String EXPECTED_SQL = "insert into location (user_id, latitude, longitude, timestamp) values (?, ?, ?, ?)";

	private TrackerDao candidate;
	
	@Mock
	private DataSource datasourceMock;
	
	@Mock
	private Connection connectionMock;
	
	private StatementConnectionCaptor connectionCaptor;
	
	@Mock
	private Statement statementMock;
	
	@Mock
	private PreparedStatement preparedStatementMock;
	
	@Before
	public void before() throws SQLException{
		DBI dbi = new DBI(datasourceMock);
		this.connectionCaptor = new StatementConnectionCaptor(connectionMock) ;
		
		when(datasourceMock.getConnection()).thenReturn(connectionCaptor);
		when(connectionMock.createStatement()).thenReturn(statementMock);
		when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
		
		this.candidate = dbi.onDemand(TrackerDao.class);
	}
	
	@Test
	public void givenValidLocation_whenCallingInsert_thenCorrectSqlGenerated() throws SQLException{
		candidate.insert(TEST_USER, TEST_LAT, TEST_LONG, TEST_TIMESTAMP);
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_USER);
		verify(preparedStatementMock, times(1)).setObject(2, TEST_LAT);
		verify(preparedStatementMock, times(1)).setObject(3, TEST_LONG);
		verify(preparedStatementMock, times(1)).setObject(4, TEST_TIMESTAMP);
	}
}
class StatementConnectionCaptor extends DelegatingConnection{
	
	private String capturedSql;
	
	public StatementConnectionCaptor(Connection connectionMock){
		super(connectionMock);
	}
	
	@Override
	public PreparedStatement prepareStatement(String sql) throws SQLException {
		this.capturedSql = sql;
		return super.prepareStatement(sql);
	}
	
	public String getCapturedSql(){
		return capturedSql;
	}
}
