package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface TrackerDao {

	  @SqlUpdate("insert into location (user_id, latitude, longitude, timestamp) values (:user_id, :latitude, :longitude, :timestamp)")
	  void insert(@Bind("user_id") int userId, @Bind("latitude") double lat, @Bind("longitude") double lng, @Bind("timestamp") long timestamp ); 
}