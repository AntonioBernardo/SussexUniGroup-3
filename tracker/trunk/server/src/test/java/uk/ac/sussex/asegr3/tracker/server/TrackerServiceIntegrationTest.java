package uk.ac.sussex.asegr3.tracker.server;

import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.junit.Test;

import com.yammer.metrics.core.HealthCheck;
import com.yammer.metrics.core.HealthCheck.Result;

import static uk.ac.sussex.asegr3.tracker.server.IntegrationTestingUtils.startEmbeddedJetty;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Verifies that the tracker service actually starts up.
 * @author andrewhaines
 *
 */
public class TrackerServiceIntegrationTest {

	private static TrackerService candidate;
	private static Server server;
	
	@BeforeClass
	public static void before() throws Exception{
		candidate = new TrackerService();
		server = startEmbeddedJetty(candidate, null);
	}
	
	@Test
	public void givenStandardTrackerService_whenCallingRun_thenServiceStartsUp() throws Exception{
		assertThat(server.isRunning(), is(equalTo(true)));
	}
	
	@Test
	public void givenDbHealthChecker_whenCallingCheck_thenPositiveResult(){
		for (HealthCheck checker: candidate.getHealthCheckers()){
			Result result = checker.execute();
			
			assertThat(""+result.getMessage(), result.isHealthy(), is(equalTo(true)));
		}
	}
}
