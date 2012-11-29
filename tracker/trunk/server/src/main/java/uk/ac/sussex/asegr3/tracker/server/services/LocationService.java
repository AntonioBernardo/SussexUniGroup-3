package uk.ac.sussex.asegr3.tracker.server.services;

import java.util.List;

import uk.ac.sussex.asegr3.comment.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationService {
	
	private static final double DEFAULT_PROXIMITY = 5;
	private static final double DEFAULT_TIME_FRESHNESS = 5;
	
	private final LocationDao dao;
	private final CommentDao commentDao;
	
	public LocationService(LocationDao dao, CommentDao commentDao){
		this.dao = dao;
		this.commentDao = commentDao;
	}
	
	public void storeLocation(String username, LocationDTO location){
		// Do the database integration here...
		// Store location in database
		
		dao.insert(username, location.getLatitude(), location.getLongitude(), location.getTimestamp());
	}
	
	public void storeLocations(String userId, List<LocationDTO> locations){
		
		dao.insert(userId, locations);
	}

	public void addComment(CommentDTO commentDTO) {
		//dao.
	}

	public List<LocationDTO> getNearbyLocations(String username) {
		// TODO fetch username current location
		
		LocationDTO currentLocation = dao.getLatestLocationForUser(username);
		
		double latMin = currentLocation.getLatitude() - DEFAULT_PROXIMITY;
		double latMax = currentLocation.getLatitude() + DEFAULT_PROXIMITY;
		
		double longMin = currentLocation.getLongitude() - DEFAULT_PROXIMITY;
		double longMax = currentLocation.getLongitude() + DEFAULT_PROXIMITY;
		
	
	return dao.getLatestLocationForUser(latMin,latMax,longMin,longMax, 0, 0);
	
	//--latmin = currentlat - proximity
	//--latmax = currentLat + proximity

}
	
}
