package ac.uk.sussex.asegr3.tracker;

import org.eclipse.jetty.server.Server;
import org.junit.BeforeClass;
import org.junit.Test;

import uk.ac.sussex.asegr3.tracker.server.TrackerService;

import static uk.ac.sussex.asegr3.tracker.server.IntegrationTestingUtils.startEmbeddedJetty;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.equalTo;

public class TrackerIntegrationTest {

	private static TrackerService candidate;
	private static Server server;
	
	@BeforeClass
	public static void before() throws Exception{
		//candidate = new TrackerService();
		//server = startEmbeddedJetty(candidate, null);
	}
	
	@Test
	public void givenLocationBatch_whenCallingAddLocationApi_thenLocationsAddedToDb(){
		
	}
}
