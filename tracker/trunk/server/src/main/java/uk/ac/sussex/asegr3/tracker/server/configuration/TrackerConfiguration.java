package uk.ac.sussex.asegr3.tracker.server.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.db.DatabaseConfiguration;

public class TrackerConfiguration extends Configuration{

	@Valid
    @NotNull
    @JsonProperty
	private DatabaseConfiguration database = new DatabaseConfiguration();
	
	@Valid
    @NotNull
    @JsonProperty
	private AuthenticationConfiguration authentication = new AuthenticationConfiguration();
	
	public DatabaseConfiguration getDatabaseConfiguration(){
		return database;
	}
	
	public AuthenticationConfiguration getAuthenticationConfiguration(){
		return authentication;
	}
}
