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
import java.sql.ResultSet;
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
public class LocationDaoUnitTest {
	
	private static final String TEST_USER = "testUser";
	private static final double TEST_LAT = 85.3453;
	private static final double TEST_LONG = 98453.5646432;
	private static final long TEST_TIMESTAMP = 34578345723L;
	private static final String EXPECTED_SQL = "insert into location (fk_user_id, latitude, longitude, timestamp_added) values ((select id from user where username=?), ?, ?, ?)";
	private static final int TEST_LIMIT = 0;
	private static final Object EXPECTED_NEARBY_LOCATION_SQL = "select * from (select * from (select locuser.username as \"locuser\", l.fk_user_id, l.id, l.latitude, l.longitude, l.timestamp_added as \"loctimestamp\", c.comments, c.image, c.timestamp_added as \"comtimestamp\"comuser.username as \"comuser\" from user locuser, location l left outer join comments c on l.id = c.fk_loc_id left outer join user comuser on c.fk_user_id = comuser.id where l.fk_user_id = locuser.id order by timestamp_added desc) orderedLocs where longitude between ? and ? and latitude between ? and ? and locuser != ? group by fk_user_id union select locuser.username as \"locuser\", l.fk_user_id, l.id, l.latitude, l.longitude, l.timestamp_added as \"loctimestamp\", c.comments, c.image, c.timestamp_added as \"comtimestamp\"comuser.username as \"comuser\" from user locuser, location l, user comuser, comments c where locuser.id = l.fk_user_id and l.id = c.fk_loc_id and c.fk_user_id = comuser.id and longitude between ? and ? and latitude between ? and ?) unlimited limit ?";

	private LocationDao candidate;
	
	@Mock
	private DataSource datasourceMock;
	
	@Mock
	private Connection connectionMock;
	
	private StatementConnectionCaptor connectionCaptor;
	
	@Mock
	private Statement statementMock;
	
	@Mock
	private PreparedStatement preparedStatementMock;
	
	@Mock
	private ResultSet resultSetMock;
	
	@Before
	public void before() throws SQLException{
		DBI dbi = new DBI(datasourceMock);
		this.connectionCaptor = new StatementConnectionCaptor(connectionMock) ;
		
		when(datasourceMock.getConnection()).thenReturn(connectionCaptor);
		when(connectionMock.createStatement()).thenReturn(statementMock);
		when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
		when(resultSetMock.next()).thenReturn(true).thenReturn(false); // 1 row by default
		when(resultSetMock.getBoolean(1)).thenReturn(true).thenReturn(false);
		when(preparedStatementMock.getResultSet()).thenReturn(resultSetMock);
		
		this.candidate = dbi.onDemand(LocationDao.class);
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
	
	@Test
	public void givenDAO_whenCallingGetNearbyLocations_thenCorrectSqlGenerated() throws SQLException{
		candidate.getNearbyLocations(TEST_USER, TEST_LAT-20, TEST_LAT+20, TEST_LONG-20, TEST_LONG+20, TEST_LIMIT);
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_NEARBY_LOCATION_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_LONG-20);
		verify(preparedStatementMock, times(1)).setObject(2, TEST_LONG+20);
		verify(preparedStatementMock, times(1)).setObject(3, TEST_LAT-20);
		verify(preparedStatementMock, times(1)).setObject(4, TEST_LAT+20);
		verify(preparedStatementMock, times(1)).setObject(5, TEST_USER);
		verify(preparedStatementMock, times(1)).setObject(6, TEST_LONG-20);
		verify(preparedStatementMock, times(1)).setObject(7, TEST_LONG+20);
		verify(preparedStatementMock, times(1)).setObject(8, TEST_LAT-20);
		verify(preparedStatementMock, times(1)).setObject(9, TEST_LAT+20);
		verify(preparedStatementMock, times(1)).setObject(10, TEST_LIMIT);
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
