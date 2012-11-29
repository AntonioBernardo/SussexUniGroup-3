package uk.ac.sussex.asegr3.tracker.server.dao;

import java.util.Set;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlBatch;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

import uk.ac.sussex.asegr3.tracker.server.dao.mapper.LocationDTOMapper;


@RegisterMapper(LocationDTOMapper.class)
public interface LocationDao {

	  @SqlUpdate("insert into location (fk_user_id, latitude, longitude, timestamp_added) values ((select id from user where username=:username), :latitude, :longitude, :timestamp)")
	  void insert(@Bind("username") String username, @Bind("latitude") double lat, @Bind("longitude") double lng, @Bind("timestamp") long timestamp ); 

	  @SqlBatch("insert into location (fk_user_id, latitude, longitude, timestamp_added) values ((select id from user where username=:username), :latitude, :longitude, :timestamp)")
	  void insert(@Bind("username") String username, @BindBean Iterable<LocationDTO> inserts);
	  
	  @SqlQuery("select * from " +
	  							"(select locuser.username as \"locuser\", " +
	  							"l.fk_user_id, " +
	  							"l.id, l.latitude, " +
	  							"l.longitude, " +
	  							"l.timestamp_added as \"loctimestamp\", " +
	  							"c.comments, " +
	  							"c.image, " +
	  							"c.timestamp_added as \"comtimestamp\", " +
	  							"comuser.username as \"comuser\" " +
	  							"from user locuser, " +
	  							"location l left outer join comments c on l.id = c.fk_loc_id " +
	  							"left outer join user comuser on c.fk_user_id = comuser.id " +
	  							"where l.fk_user_id = locuser.id " +
	  							"order by l.timestamp_added desc) " +
	  					"orderedLocs where " +
	  					"locuser = :username limit 1")
	  LocationDTO getLatestLocationForUser(@Bind("username") String username);
	  

	  @SqlQuery("select * from (" +
	  					"select * from " +
	  							"(select locuser.username as \"locuser\", " +
	  							"l.fk_user_id, " +
	  							"l.id, l.latitude, " +
	  							"l.longitude, " +
	  							"l.timestamp_added as \"loctimestamp\", " +
	  							"c.comments, " +
	  							"c.image, " +
	  							"c.timestamp_added as \"comtimestamp\", " +
	  							"comuser.username as \"comuser\" " +
	  							"from user locuser, " +
	  							"location l left outer join comments c on l.id = c.fk_loc_id " +
	  							"left outer join user comuser on c.fk_user_id = comuser.id " +
	  							"where l.fk_user_id = locuser.id " +
	  							"order by l.timestamp_added desc) " +
	  					"orderedLocs where " +
	  					"longitude between :longMin and :longMax " +
	  					"and latitude between :latMin and :latMax " +
	  					"and locuser != :currentuser " +
	  					"group by fk_user_id " +
	  					
			  			"union " +
			  			
			  			"select locuser.username as \"locuser\", " +
			  			"l.fk_user_id, l.id, " +
			  			"l.latitude, " +
			  			"l.longitude, " +
			  			"l.timestamp_added as \"loctimestamp\", " +
			  			"c.comments, " +
			  			"c.image, " +
			  			"c.timestamp_added as \"comtimestamp\", " +
			  			"comuser.username as \"comuser\" " +
			  			"from user locuser, " +
			  			"location l, " +
			  			"user comuser, " +
			  			"comments c " +
			  			"where locuser.id = l.fk_user_id " +
			  			"and l.id = c.fk_loc_id " +
			  			"and c.fk_user_id = comuser.id " +
			  			"and longitude between :longMin and :longMax " +
			  			"and latitude between :latMin and :latMax) " +
			  	"unlimited limit :rowLimit")
	  Set<LocationDTO> getNearbyLocations(@Bind("currentuser") String currentUsername, @Bind("latMin") double latMin, @Bind("latMax") double latMax, @Bind("longMin") double longMin, @Bind("longMax") double longMax, @Bind("rowLimit") int limit);
}

