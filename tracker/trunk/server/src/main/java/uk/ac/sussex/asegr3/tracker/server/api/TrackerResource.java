package uk.ac.sussex.asegr3.tracker.server.api;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;
import uk.ac.sussex.asegr3.tracker.server.services.LocationService;
import uk.ac.sussex.asegr3.transport.beans.TransportLocation;
import uk.ac.sussex.asegr3.transport.beans.TransportLocationBatch;

@Path("/tracker")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TrackerResource {
	
	private final LocationService serviceProvider;
	
	public TrackerResource(LocationService serviceProvider){
		
		this.serviceProvider = serviceProvider;
	}

	@POST
	public void addLocation(TransportLocation location){
		
		LocationDTO locationDetails=
				new LocationDTO(location.getLattitude(), location.getLongitude(), location.getTimeStamp());
		
		serviceProvider.storeLocation(locationDetails);
		
	}
	
	@POST
	@Path("/batch")
	public void addLocations(TransportLocationBatch transportLocations){
		
		List<LocationDTO> locations=new ArrayList<LocationDTO>();
		
		for(TransportLocation transportLocation : transportLocations.getLocations()){
			LocationDTO locationDetails=new LocationDTO(transportLocation.getLattitude(), 
					transportLocation.getLongitude(), transportLocation.getTimeStamp());
			
			locations.add(locationDetails);
		}
		
		serviceProvider.storeLocations(locations);
	}
}
