package uk.ac.sussex.asegr3.tracker.server;

import uk.ac.sussex.asegr3.tracker.server.api.TrackerResource;
import uk.ac.sussex.asegr3.tracker.server.configuration.TrackerConfiguration;
import uk.ac.sussex.asegr3.tracker.server.healthcheck.DatabaseHealthCheck;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;

public class TrackerService extends Service<TrackerConfiguration>{

	@Override
	protected void initialize(TrackerConfiguration configuration, Environment environment) throws Exception {
		environment.addResource(new TrackerResource());
		environment.addHealthCheck(new DatabaseHealthCheck(null)); // replace null with database once this has been setup
	}
	
	public static void main(String[] args) throws Exception{
		new TrackerService().run(args);
	}

}
