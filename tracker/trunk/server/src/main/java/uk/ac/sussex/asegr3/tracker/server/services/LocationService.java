package uk.ac.sussex.asegr3.tracker.server.services;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.server.dao.TrackerDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationService {
	
	private final TrackerDao dao;
	
	public LocationService(TrackerDao dao){
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
