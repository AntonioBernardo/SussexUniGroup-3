package uk.ac.sussex.asegr3.tracker.server.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonProperty;

public class AuthenticationConfiguration {

	@Valid
    @NotNull
    @JsonProperty
	private int sessionTTLSecs;

	public int getSessionTTLSecs() {
		return sessionTTLSecs;
	}
}
