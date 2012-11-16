package uk.ac.sussex.asegr3.tracker.security;

import java.security.SignatureException;

import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import com.google.common.base.Optional;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

public class UserAuthenticator implements Authenticator<TransportAuthenticationToken, LoggedInUser>{

	private final AuthenticationService authService;
	
	public UserAuthenticator(AuthenticationService authService){
		this.authService = authService;
	}
	@Override
	public Optional<LoggedInUser> authenticate(TransportAuthenticationToken credentials) throws AuthenticationException {
		try {
			if (authService.validateToken(credentials)){
				return Optional.of(new LoggedInUser(credentials.getExpires(), credentials.getUsername()));
			} else{
				return Optional.absent();
			}
		} catch (SignatureException e) {
			throw new AuthenticationException(e);
		}
	}

}
