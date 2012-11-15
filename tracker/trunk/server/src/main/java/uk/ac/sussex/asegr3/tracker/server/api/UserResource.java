package uk.ac.sussex.asegr3.tracker.server.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@POST
	public void createNewUser(){
	
	}
	
	@POST
	public void authenticateUser(){
		
	}
}
