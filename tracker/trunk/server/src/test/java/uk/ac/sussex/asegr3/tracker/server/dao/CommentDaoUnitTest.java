package uk.ac.sussex.asegr3.tracker.server.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import uk.ac.sussex.asegr3.comment.server.dao.CommentDao;

@RunWith(MockitoJUnitRunner.class)
public class CommentDaoUnitTest {
	private static final String TEST_USER = "testuser";
	private static final String TEST_TEXT = "testtext";
	private static final int TEST_LOCATION = 98453;
	private static final byte[] TEST_IMAGE = new byte[]{0,2,4,5,6};
	private static final String EXPECTED_SQL = "insert into comment (fk_user_id, text, location_id, image) values ((select id from user where username=?), ?, ?, ?)";

	private CommentDao candidate;
	
	@Mock
	private DataSource datasourceMock;
	
	@Mock
	private Connection connectionMock;
	
	private StatementsConnectionCaptor connectionCaptor;
	
	@Mock
	private Statement statementMock;
	
	@Mock
	private PreparedStatement preparedStatementMock;
	
	@Before
	public void before() throws SQLException{
		DBI dbi = new DBI(datasourceMock);
		this.connectionCaptor = new StatementsConnectionCaptor(connectionMock) ;
		
		when(datasourceMock.getConnection()).thenReturn(connectionCaptor);
		when(connectionMock.createStatement()).thenReturn(statementMock);
		when(connectionMock.prepareStatement(any(String.class))).thenReturn(preparedStatementMock);
		
		this.candidate = dbi.onDemand(CommentDao.class);
	}
	
	@Test
	public void givenValidComment_whenCallingInsert_thenCorrectSqlGenerated() throws SQLException{
		candidate.insert(TEST_USER, TEST_TEXT, TEST_LOCATION, TEST_IMAGE);
		
		verify(preparedStatementMock, times(1)).execute();
		assertThat(connectionCaptor.getCapturedSql(), is(equalTo(EXPECTED_SQL)));
		
		verify(preparedStatementMock, times(1)).setObject(1, TEST_USER);
		verify(preparedStatementMock, times(1)).setObject(2, TEST_TEXT);
		verify(preparedStatementMock, times(1)).setObject(3, TEST_LOCATION);
		verify(preparedStatementMock, times(1)).setObject(4, TEST_IMAGE);
	}
}
class StatementsConnectionCaptor extends DelegatingConnection{
	
	private String capturedSql;
	
	public StatementsConnectionCaptor(Connection connectionMock){
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
