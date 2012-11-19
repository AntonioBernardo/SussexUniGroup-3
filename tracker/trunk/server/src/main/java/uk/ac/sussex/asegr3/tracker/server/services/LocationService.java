package uk.ac.sussex.asegr3.tracker.server.services;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationService {
	
	private final LocationDao dao;
	
	public LocationService(LocationDao dao){
		this.dao = dao;
	}
	
	public void storeLocation(LocationDTO location){
		// Do the database integration here...
		// Store location in database
		
		dao.insert(1, location.getLatitude(), location.getLongitude(), location.getTimeStamp());
	}
	
	public void storeLocations(List<LocationDTO> locations){
		
		if(locations!=null){
			for(LocationDTO location : locations){
				// Do the database integration here...
				// Store location in database
				storeLocation(location);
			}
		}
	}

}
