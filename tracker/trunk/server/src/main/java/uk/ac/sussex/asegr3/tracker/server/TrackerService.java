package uk.ac.sussex.asegr3.tracker.server;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.sussex.asegr3.tracker.security.CookieSigAuthProvider;
import uk.ac.sussex.asegr3.tracker.security.LoggedInUser;
import uk.ac.sussex.asegr3.tracker.security.UserAuthenticator;
import uk.ac.sussex.asegr3.comment.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.api.LocationResource;
import uk.ac.sussex.asegr3.tracker.server.api.UserResource;
import uk.ac.sussex.asegr3.tracker.server.configuration.TrackerConfiguration;
import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.healthcheck.DatabaseHealthCheck;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;
import uk.ac.sussex.asegr3.tracker.server.services.UserService;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

import com.sun.jersey.api.core.ResourceConfig;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.db.Database;
import com.yammer.dropwizard.db.DatabaseConfiguration;
import com.yammer.dropwizard.db.DatabaseFactory;
import com.yammer.metrics.core.HealthCheck;

public class TrackerService extends Service<TrackerConfiguration>{

	private final Logger LOG = LoggerFactory.getLogger(TrackerService.class);
	private final List<HealthCheck> healthCheckers;
	
	public TrackerService(){
		this.healthCheckers = new LinkedList<HealthCheck>();
	}
	@Override
	protected void initialize(TrackerConfiguration configuration, Environment environment) throws Exception {
		
		
		LOG.debug("setting up tracker service");
		// utils
		
		Clock clock = new SystemClock();
		
		// daos
		Database database = createDatabase(environment, configuration.getDatabaseConfiguration());
		LocationDao locationDao = database.onDemand(LocationDao.class);
		UserDao userDao = database.onDemand(UserDao.class);
		CommentDao commentDao = database.onDemand(CommentDao.class);
		
		//services 
		
		AuthenticationService authService = createAuthenticationService(userDao, configuration, clock);
		UserService userService = new UserService(userDao, authService);
		
		// health checks
		addHealthCheck(environment, new DatabaseHealthCheck(database));
		
		// resources
		environment.addResource(new LocationResource(new LocationService(locationDao,commentDao)));
		environment.addResource(new UserResource(authService, userService));
		environment.enableJerseyFeature(ResourceConfig.FEATURE_TRACE);
		
		// providers
		environment.addProvider(new CookieSigAuthProvider<LoggedInUser>(new UserAuthenticator(authService),
                "User_Authenticator"));
		
		LOG.debug("tracker service setup");
	}
	
	private AuthenticationService createAuthenticationService(UserDao userDao, TrackerConfiguration conf, Clock clock) {
		return new AuthenticationService(userDao, conf.getAuthenticationConfiguration().getSessionTTLSecs(), clock);
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
