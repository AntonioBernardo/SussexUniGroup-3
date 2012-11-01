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
		try {
			database.ping();
			return Result.healthy();
		} catch (Throwable t){
			return Result.unhealthy(t);
		}
	}

}
