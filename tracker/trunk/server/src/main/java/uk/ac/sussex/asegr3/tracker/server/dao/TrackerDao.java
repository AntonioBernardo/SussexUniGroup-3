package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface TrackerDao {

	  @SqlUpdate("insert into location (user_id, latitude, longitude, timestamp) values (:user_id, :latitude, :longitude, :timestamp)")
	  void insert(@Bind("user_id") int userId, @Bind("latitude") double lat, @Bind("longitude") double lng, @Bind("timestamp") long timestamp ); 

	  //@SqlBatch("insert into location (user_id, latitude, longitude, timestamp) values (:user_id, :latitude, :longitude, :timestamp)")
	  //void insert(@BindBean Iterable<LocationDTO> inserts);
	  
	  @SqlQuery("select username from user where id = :id")
	  String findNameById(@Bind("id") int id) ;
}