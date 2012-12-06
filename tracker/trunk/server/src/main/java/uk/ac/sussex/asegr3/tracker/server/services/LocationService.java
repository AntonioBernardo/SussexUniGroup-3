package uk.ac.sussex.asegr3.tracker.server.services;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import uk.ac.sussex.asegr3.tracker.server.dao.CommentDao;
import uk.ac.sussex.asegr3.tracker.server.dao.LocationDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationService {
	
	private static final double DEFAULT_PROXIMITY = 5;
	private static final int DEFAULT_LIMIT = 20;
	
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
		commentDao.insert(commentDTO.getUsername(), commentDTO.getText(), commentDTO.getLocationID(), commentDTO.getImage(), System.currentTimeMillis());
	}

	public Set<LocationDTO> getNearbyLocations(String username) {
		
		LocationDTO currentLocation = dao.getLatestLocationForUser(username);
		if (currentLocation != null){
			double latMin = currentLocation.getLatitude() - DEFAULT_PROXIMITY;
			double latMax = currentLocation.getLatitude() + DEFAULT_PROXIMITY;
			
			double longMin = currentLocation.getLongitude() - DEFAULT_PROXIMITY;
			double longMax = currentLocation.getLongitude() + DEFAULT_PROXIMITY;
		
			return dao.getNearbyLocations(username, latMin, latMax, longMin, longMax, DEFAULT_LIMIT);
		} else{
			return Collections.emptySet();
		}
	}
}
