package uk.ac.sussex.asegr3.tracker.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.auth.Auth;

import uk.ac.sussex.asegr3.tracker.security.LoggedInUser;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationToken;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.SecurityViolationException;
import uk.ac.sussex.asegr3.transport.beans.AbstractTransportUserRequest.Gender;
import uk.ac.sussex.asegr3.transport.beans.InvalidEmailException;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationRequest;
import uk.ac.sussex.asegr3.transport.beans.TransportAuthenticationToken;
import uk.ac.sussex.asegr3.transport.beans.TransportErrorResponse;
import uk.ac.sussex.asegr3.transport.beans.TransportErrorResponse.ErrorCode;
import uk.ac.sussex.asegr3.transport.beans.TransportNewUserRequest;
import uk.ac.sussex.asegr3.transport.beans.TransportUserProfile;
import uk.ac.sussex.asegr3.tracker.server.services.UserAlreadyExistsException;
import uk.ac.sussex.asegr3.tracker.server.services.UserNotFoundOrPermissionedException;
import uk.ac.sussex.asegr3.tracker.server.services.UserService;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {
	
	private final AuthenticationService authenticationService;
	private final UserService userService;
	private final Logger LOG = LoggerFactory.getLogger(UserResource.class);
	
	public UserResource(AuthenticationService authenticationService, UserService userService){
		this.authenticationService = authenticationService;
		this.userService = userService;
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
					.entity(new TransportErrorResponse(ErrorCode.INVALID_CREDENTIALS, "You're authorisation credentials are not correct"))
											  .build());
		}
	}
	
	@Path("/{username}/")
	@POST
	public TransportAuthenticationToken signup(TransportNewUserRequest newUserRequest, @PathParam("username") String username){
		if (!username.equals(newUserRequest.getEmail())){
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity(new TransportErrorResponse(ErrorCode.INVALID_API_REQUEST, "url username context does not match request payload"))
					.build());
			
		}
		try {
			userService.addNewUser(username, 
					newUserRequest.getPassword(), 
					newUserRequest.getName(), 
					newUserRequest.getSurname(), 
					newUserRequest.getAge(), 
					newUserRequest.getGender().name(), 
					newUserRequest.getAboutYou(), 
					newUserRequest.getInterests());
			
			return authenticateUser(username, new TransportAuthenticationRequest(newUserRequest.getPassword()));
		} catch (UserAlreadyExistsException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity(new TransportErrorResponse(ErrorCode.USER_ALREADY_EXISTS, "username: "+username+" already exists"))
					.build());
		}
	}
	
	@Path("/{username}")
	@GET
	public TransportUserProfile getProfile(@Auth LoggedInUser loggedInUser, @PathParam("username") String username){
		UserDTO user = null;
		try {
			user = userService.getUserProfile(username, loggedInUser.getUsername());
			
			return new TransportUserProfile(user.getEmail(), user.getName(), user.getSurname(), user.getAge(), Gender.valueOf(user.getGender().name()), user.getAbout(), user.getInterests(), user.getLastLogginDate(), user.getSignupDate());
		} catch (UserNotFoundOrPermissionedException e) {
			throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
					.entity(new TransportErrorResponse(ErrorCode.NOT_PERMISSIONED, "Either the user: "+username+" does not exist or you are not permissioned to view their profile"))
					.build());
		} catch (InvalidEmailException e) {
			try {
				return new TransportUserProfile("unknownUsername@unknown.com", user.getName(), user.getSurname(), user.getAge(), Gender.valueOf(user.getGender().name()), user.getAbout(), user.getInterests(), user.getLastLogginDate(), user.getSignupDate());
			} catch (InvalidEmailException e1) {
				throw new WebApplicationException(Response.status(Response.Status.BAD_REQUEST)
						.entity(new TransportErrorResponse(ErrorCode.UNKNOWN_ERROR, "There is something very wrong with an email address for user: "+username))
						.build());
			}
		}
	}
}
