package uk.ac.sussex.asegr3.tracker.server.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationDTOMapper implements ResultSetMapper<LocationDTO>{

	@Override
	public LocationDTO map(int rowNum, ResultSet resultset, StatementContext context) throws SQLException {
		
		String username = resultset.getString("user");
		double lat = resultset.getDouble("lat");
		double lng = resultset.getDouble("long");
		long timestamp = resultset.getLong("time");
		
		LocationDTO loc = new LocationDTO(username, lat, lng, timestamp);
		
		return loc;
	}

}
