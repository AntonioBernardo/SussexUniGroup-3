package uk.ac.sussex.asegr3.tracker.server.services;

import java.util.List;

import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationService {
	
	private static final double DEFAULT_PROXIMITY = 5;
	private static final double DEFAULT_TIME_FRESHNESS = 5;
	
	private final LocationDao dao;
	
	public LocationService(LocationDao dao){
		this.dao = dao;
	}
	
	public void storeLocation(LocationDTO location){
		// Do the database integration here...
		// Store location in database
		
		dao.insert(location.getUsername(), location.getLatitude(), location.getLongitude(), location.getTimeStamp());
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

	public List<LocationDTO> getNearbyLocations(String username) {
		// TODO fetch username current location
		
		LocationDTO currentLocation = dao.getLatestLocationForUser(username);
		
		double latMin = currentLocation.getLatitude() - DEFAULT_PROXIMITY;
		double latMax = currentLocation.getLatitude() + DEFAULT_PROXIMITY;
		
		return null;
		//dao.getLocations(currentLocation.getLatitude(), currentLocation.getLongitude(), currentLocation.getTimeStamp(), currentLocation.getLatMin(),currentLocation.getLatMax(),currentLocation.getLongMin(),currentLocation.getLongMax(),currentLocation.getTimeMin(),currentLocation.getTimeMax());
		//(double latMin, double latMax, double longMin, double longMax, long timeMin, long timeMax);
	}

}
