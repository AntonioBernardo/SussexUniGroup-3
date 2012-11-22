package uk.ac.sussex.asegr3.tracker.security;

import java.security.SignatureException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException.Type;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

public class UserAuthenticator implements Authenticator<TransportAuthenticationToken, LoggedInUser>{

	private final Logger LOG = LoggerFactory.getLogger(UserAuthenticator.class);
	private final AuthenticationService authService;
	
	public UserAuthenticator(AuthenticationService authService){
		this.authService = authService;
	}
	@Override
	public Optional<LoggedInUser> authenticate(TransportAuthenticationToken credentials) throws AuthenticationException {
		
		LOG.debug("attempting to validate token from user: "+credentials.getUsername());
		try {
			if (authService.validateToken(credentials)){
				return Optional.of(new LoggedInUser(credentials.getExpires(), credentials.getUsername()));
			} else{
				return Optional.absent();
			}
		} catch (SignatureException e) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

}
