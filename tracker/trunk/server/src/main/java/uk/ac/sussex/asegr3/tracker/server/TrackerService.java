package uk.ac.sussex.asegr3.tracker.server;

import java.util.LinkedList;
import java.util.List;

import uk.ac.sussex.asegr3.tracker.server.api.TrackerResource;
import uk.ac.sussex.asegr3.tracker.server.configuration.TrackerConfiguration;
import uk.ac.sussex.asegr3.tracker.server.dao.TrackerDao;
import uk.ac.sussex.asegr3.tracker.server.healthcheck.DatabaseHealthCheck;

import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.metrics.core.HealthCheck;

public class TrackerService extends Service<TrackerConfiguration>{

	private final List<HealthCheck> healthCheckers;
	
	public TrackerService(){
		this.healthCheckers = new LinkedList<HealthCheck>();
	}
	@Override
	protected void initialize(TrackerConfiguration configuration, Environment environment) throws Exception {
		
		Database database = createDatabase(environment, configuration.getDatabaseConfiguration());
		TrackerDao dao = database.onDemand(TrackerDao.class);
		
		addHealthCheck(environment, new DatabaseHealthCheck(database));
		
		environment.addResource(new TrackerResource());
	}
	
	public List<HealthCheck> getHealthCheckers(){
		return healthCheckers;
	}
	
	private void addHealthCheck(Environment environment, HealthCheck healthCheck) {
		environment.addHealthCheck(healthCheck);
		healthCheckers.add(healthCheck);
	}
	private Database createDatabase(Environment environment, DatabaseConfiguration configuration) throws ClassNotFoundException {
		final DatabaseFactory factory = new DatabaseFactory(environment);
	    final Database db = factory.build(configuration, "mysql");
	    
	    return db;
	}

	public static void main(String[] args) throws Exception{
		new TrackerService().run(args);
	}

}
