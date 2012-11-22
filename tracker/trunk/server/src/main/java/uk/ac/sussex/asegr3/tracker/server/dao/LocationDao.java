package uk.ac.sussex.asegr3.tracker.server.dao;

import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

import uk.ac.sussex.asegr3.tracker.server.dao.mapper.LocationDTOMapper;

@RegisterMapper(LocationDTOMapper.class)
public interface LocationDao {

	  @SqlUpdate("insert into location (fk_user_id, latitude, longitude, timestamp_added) values ((select id from user where username=:username), :latitude, :longitude, :timestamp)")
	  void insert(@Bind("username") String username, @Bind("latitude") double lat, @Bind("longitude") double lng, @Bind("timestamp") long timestamp ); 

	  @SqlQuery("select u.username as \"user\", loc.latitude as \"lat\", loc.longitude as \"long\", loc.timestamp_added as \"time\" from tracker.user u, tracker.location loc where u.id = loc.fk_user_id")
	  List<LocationDTO> getLocations(double latMin, double latMax, double longMin, double longMax, long timeMin, long timeMax, int numRowsToReturn);

	  @SqlQuery("select u.username as \"user\", loc.latitude as \"lat\", loc.longitude as \"long\", loc.timestamp_added as \"time\" from tracker.user u, tracker.location loc where u.id = loc.fk_user_id and u.username=:username order by loc.timestamp_added desc limit 1")
	  LocationDTO getLatestLocationForUser(@Bind("username") String username);
	  
	  //--get UNION to display location stamp_added range and location longitude and latitude range
	  @SqlQuery("select u.username as \"user\", loc.latitude as \"lat\", loc.longitude as \"long\", loc.timestamp_added as \"time\" from tracker.user u, tracker.location loc where u.id = loc.fk_user_id and loc.timestamp_added between :timeMin and :timeMax union select u.username as \"user\", loc.latitude as \"lat\", loc.longitude as \"long\", loc.timestamp_added as \"time\" from tracker.user u, tracker.location loc where u.id = loc.fk_user_id and loc.latitude between :latMin and :latMax and loc.longitude between :longMin and :longMax")
	  List<LocationDTO> getLocationsTimestampRange(double latMin, double latMax, double longMin, double longMax, long timeMin, long timeMax);

	  //--get and display location longitude and latitude range
	  //@SqlQuery("select u.username as \"user\", loc.latitude as \"lat\", loc.longitude as \"long\", loc.timestamp_added as \"time\" from tracker.user u, tracker.location loc where u.id = loc.fk_user_id and loc.latitude between :latMin and :latMax and loc.longitude between :longMin and :longMax")																																				
	 // List<LocationDTO> getLocationsRange(double latMin, double latMax, double longMin, double longMax, long timeMin, long timeMax);

	  


}
