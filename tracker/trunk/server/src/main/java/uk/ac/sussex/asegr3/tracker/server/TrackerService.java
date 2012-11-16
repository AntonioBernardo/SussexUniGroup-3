package uk.ac.sussex.asegr3.tracker.server;

import java.util.LinkedList;
import java.util.List;

import uk.ac.sussex.asegr3.tracker.security.CookieSigAuthProvider;
import uk.ac.sussex.asegr3.tracker.security.LoggedInUser;
import uk.ac.sussex.asegr3.tracker.security.UserAuthenticator;
import uk.ac.sussex.asegr3.tracker.server.api.LocationResource;
import uk.ac.sussex.asegr3.tracker.server.api.UserResource;
import uk.ac.sussex.asegr3.tracker.server.configuration.TrackerConfiguration;
import uk.ac.sussex.asegr3.tracker.server.dao.TrackerDao;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.healthcheck.DatabaseHealthCheck;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

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
		
		// utils
		
		Clock clock = new SystemClock();
		
		// daos
		Database database = createDatabase(environment, configuration.getDatabaseConfiguration());
		TrackerDao dao = database.onDemand(TrackerDao.class);
		UserDao userDao = database.onDemand(UserDao.class);
		
		//services 
		
		AuthenticationService authService = createAuthenticationService(userDao, configuration, clock);
		
		// health checks
		addHealthCheck(environment, new DatabaseHealthCheck(database));
		
		
		// resources
		environment.addResource(new LocationResource(new LocationService(dao)));
		environment.addResource(new UserResource(authService));
		
		// providers
		environment.addProvider(new CookieSigAuthProvider<LoggedInUser>(new UserAuthenticator(authService),
                "User_Authenticator"));
	}
	
	private AuthenticationService createAuthenticationService(UserDao userDao, TrackerConfiguration conf, Clock clock) {
		return new AuthenticationService(userDao, conf.getAuthenticationConfiguration().getSessionTTL(), clock);
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
