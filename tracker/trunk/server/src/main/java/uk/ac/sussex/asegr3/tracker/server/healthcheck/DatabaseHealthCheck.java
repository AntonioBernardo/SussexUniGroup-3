package uk.ac.sussex.asegr3.tracker.server.healthcheck;

import com.yammer.dropwizard.db.Database;
import com.yammer.metrics.core.HealthCheck;

public class DatabaseHealthCheck extends HealthCheck{

	private final Database database;
	
	public DatabaseHealthCheck(Database database) {
		super("database");
		this.database = database;
	}

	@Override
	protected Result check() throws Exception {
		// TODO once database has been setup
		return null;
	}

}
