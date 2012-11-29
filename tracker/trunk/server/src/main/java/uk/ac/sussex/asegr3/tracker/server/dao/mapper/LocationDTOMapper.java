package uk.ac.sussex.asegr3.tracker.server.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.LocationDTO;

public class LocationDTOMapper implements ResultSetMapper<LocationDTO>{

	private static final String COMMENT_CONTEXT_KEY = "loc_comments";
	
	@Override
	public LocationDTO map(int rowNum, ResultSet resultset, StatementContext context) throws SQLException {
		Map<Integer, Collection<CommentDTO>> locComments = (Map<Integer, Collection<CommentDTO>>)context.getAttribute(COMMENT_CONTEXT_KEY);
		if (locComments == null){
			locComments = new HashMap<Integer, Collection<CommentDTO>>();
			context.setAttribute(COMMENT_CONTEXT_KEY, locComments);
		}
		
		int locId = resultset.getInt("id");
		
		Collection<CommentDTO> comments = locComments.get(locId);
		String comment = resultset.getString("comments");
		byte[] image = resultset.getBytes("image");
		String comuser = resultset.getString("comuser");
		long commentTimestamp = resultset.getLong("comtimestamp");
		CommentDTO commentDto = new CommentDTO(comuser, comment, locId, image, commentTimestamp);
		if (comments == null) {
			comments = new LinkedList<CommentDTO>();
		}
		comments.add(commentDto);
		locComments.put(locId, comments);
		
		double lat = resultset.getDouble("latitude");
		double lng = resultset.getDouble("longitude");
		long timestamp = resultset.getLong("loctimestamp");
		String username = resultset.getString("locuser");
		
		return new LocationDTO(locId, username, lat, lng, timestamp, comments);
	}

}
