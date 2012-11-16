package uk.ac.sussex.asegr3.tracker.security;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

import com.google.common.base.Optional;
import com.sun.jersey.api.core.HttpContext;
import com.sun.jersey.api.model.Parameter;
import com.sun.jersey.core.spi.component.ComponentContext;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.server.impl.inject.AbstractHttpContextInjectable;
import com.sun.jersey.spi.inject.Injectable;
import com.sun.jersey.spi.inject.InjectableProvider;
import com.yammer.dropwizard.auth.Auth;
import com.yammer.dropwizard.auth.AuthenticationException;
import com.yammer.dropwizard.auth.Authenticator;

public class CookieSigAuthProvider<T> implements InjectableProvider<Auth, Parameter> {

	public static final Logger LOG = LoggerFactory.getLogger(CookieSigAuthProvider.class);
	public static final String AUTHENTICATION_SIGNATURE_COOKIE_NAME = "__uk.ac.sussex.asegr3.tracker.AUTH";
	
	private final Authenticator<TransportAuthenticationToken, T> authenticator;
	private final String realm;
	
	public CookieSigAuthProvider(Authenticator<TransportAuthenticationToken, T> authenticator, String realm){
		this.authenticator = authenticator;
		this.realm = realm;
	}
	
	@Override
    public ComponentScope getScope() {
        return ComponentScope.PerRequest;
    }

	@Override
	public Injectable<T> getInjectable(ComponentContext ic, Auth a, Parameter c) {
		return new CookieSigAuthInjectable<T>(authenticator, realm, a.required());
	}

	static class CookieSigAuthInjectable<T> extends AbstractHttpContextInjectable<T>{
		private static final String PREFIX = "Basic";
	    private static final String HEADER_NAME = "WWW-Authenticate";
	    private static final String HEADER_VALUE = PREFIX + " realm=\"%s\"";
		
		private final Authenticator<TransportAuthenticationToken, T> authenticator;
	    private final String realm;
	    private final boolean required;
	    
	    public CookieSigAuthInjectable(Authenticator<TransportAuthenticationToken, T> authenticator, String realm, boolean required) {
			this.authenticator = authenticator;
			this.realm = realm;
			this.required = required;
		}

		@Override
		public T getValue(HttpContext c) {
			Cookie cookie = c.getRequest().getCookies().get(AUTHENTICATION_SIGNATURE_COOKIE_NAME);
			if (cookie != null){
				String token = cookie.getValue();
				TransportAuthenticationToken transportToken = TransportAuthenticationToken.createAuthenticationTokenFromString(token);
				try{
					final Optional<T> result = authenticator.authenticate(transportToken);
	                if (result.isPresent()) {
	                    return result.get();
	                }
				} catch (AuthenticationException e){
					CookieSigAuthProvider.LOG.warn("Error authenticating credentials", e);
		            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
				}
			}
			if (required){
				throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                        .header(HEADER_NAME,
                                String.format(HEADER_VALUE, realm))
                        .entity("Credentials are required to access this resource.")
                        .type(MediaType.TEXT_PLAIN_TYPE)
                        .build());
			}
			return null;
		}
	}
}
