package uk.ac.sussex.asegr3.tracker.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationToken;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationRequest;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final AuthenticationService authenticationService;
	private final Logger LOG = LoggerFactory.getLogger(UserResource.class);
	
	public UserResource(AuthenticationService authenticationService){
		this.authenticationService = authenticationService;
	}

	@POST
	public void createNewUser(){
	
	}
	
	@Path("/{username}/authenticate")
	@POST
	public TransportAuthenticationToken authenticateUser(@PathParam("username") String username, TransportAuthenticationRequest request){
		LOG.debug("authenticating user: "+username);
		try{
			AuthenticationToken token = authenticationService.authenticateUser(username, request.getPassword());
		
			return new TransportAuthenticationToken(token.getUsername(), token.getSignature(), token.getExpires());
		} catch (SecurityViolationException e){
			throw new WebApplicationException(Response.status(Response.Status.FORBIDDEN)
											  .build());
		}
	}
}
