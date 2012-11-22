package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface LocationDao {

	  @SqlUpdate("insert into location (fk_user_id, latitude, longitude, timestamp_added) values (:user_id, :latitude, :longitude, :timestamp)")
	  void insert(@Bind("user_id") int userId, @Bind("latitude") double lat, @Bind("longitude") double lng, @Bind("timestamp") long timestamp ); 
}