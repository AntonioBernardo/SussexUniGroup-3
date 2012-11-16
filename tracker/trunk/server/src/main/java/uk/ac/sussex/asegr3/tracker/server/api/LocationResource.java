package uk.ac.sussex.asegr3.tracker.server.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.yammer.dropwizard.auth.Auth;

import uk.ac.sussex.asegr3.tracker.security.LoggedInUser;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationResource {
	
	private final LocationService locationService;
	
	public LocationResource(LocationService serviceProvider){
		
		this.locationService = serviceProvider;
	}

	@POST
	public void addLocation(@Auth LoggedInUser user, TransportLocation location){
		
		LocationDTO locationDetails=
				new LocationDTO(location.getLattitude(), location.getLongitude(), location.getTimeStamp());
		
		locationService.storeLocation(locationDetails);
		
	}
	
	@POST
	@Path("/batch")
	public void addLocations(@Auth LoggedInUser user, TransportLocationBatch transportLocations){
		
		List<LocationDTO> locations=new ArrayList<LocationDTO>();
		
		for(TransportLocation transportLocation : transportLocations.getLocations()){
			LocationDTO locationDetails=new LocationDTO(transportLocation.getLattitude(), 
					transportLocation.getLongitude(), transportLocation.getTimeStamp());
			
			locations.add(locationDetails);
		}
		
		locationService.storeLocations(locations);
	}
}
