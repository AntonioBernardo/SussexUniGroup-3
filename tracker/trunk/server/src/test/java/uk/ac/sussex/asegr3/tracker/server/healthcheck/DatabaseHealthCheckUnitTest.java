package uk.ac.sussex.asegr3.tracker.server.healthcheck;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.yammer.dropwizard.db.Database;

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
		candidate.check();
	}
	
	@Test
	public void givenUnHealthyDatabase_whenCallingCheck_thenReturnsFalse() throws Exception{
		candidate.check();
	}
}
