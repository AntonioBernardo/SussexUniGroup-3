package uk.ac.sussex.asegr3.tracker.server.healthcheck;

import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yammer.dropwizard.db.Database;
import com.yammer.metrics.core.HealthCheck.Result;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.doThrow;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseHealthCheckUnitTest {

	private DatabaseHealthCheck candidate;
	
	@Mock
	private Database databaseMock;
	
	@Before
	public void before(){
		candidate = new DatabaseHealthCheck(databaseMock);
	}
	
	@Test
	public void givenHealthyDatabase_whenCallingCheck_thenReturnsTrue() throws Exception{
		Result result = candidate.check();
		assertThat(result.isHealthy(), is(equalTo(true)));
	}
	
	@Test
	public void givenUnHealthyDatabase_whenCallingCheck_thenReturnsFalse() throws Exception{
		doThrow(new SQLException("Error")).when(databaseMock).ping();
		Result result = candidate.check();
		
		assertThat(result.isHealthy(), is(equalTo(false)));
	}
}
