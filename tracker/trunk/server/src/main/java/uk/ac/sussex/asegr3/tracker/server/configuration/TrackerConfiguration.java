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
	
	public DatabaseConfiguration getDatabaseConfiguration(){
		return database;
	}
}
