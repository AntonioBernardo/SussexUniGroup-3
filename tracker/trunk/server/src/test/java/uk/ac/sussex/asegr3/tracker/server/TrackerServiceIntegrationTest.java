package uk.ac.sussex.asegr3.tracker.server;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.eclipse.jetty.server.Server;
import org.junit.Before;
import org.junit.Test;

import static uk.ac.sussex.asegr3.tracker.server.IntegrationTestingUtils.startEmbeddedJetty;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

/**
 * Verifies that the tracker service actually starts up.
 * @author andrewhaines
 *
 */
public class TrackerServiceIntegrationTest implements ServletContextListener {

	private TrackerService candidate;
	private Server server;
	private boolean startedUp = false;
	
	@Before
	public void before() throws Exception{
		candidate = new TrackerService();
		server = startEmbeddedJetty(candidate, this);
	}
	
	@Test
	public void givenStandardTrackerService_whenCallingRun_thenServiceStartsUp() throws Exception{
		assertThat(server.isRunning(), is(equalTo(true)));
		assertThat(startedUp, is(equalTo(true)));
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		this.startedUp = true;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		this.startedUp = false;
	}
}
