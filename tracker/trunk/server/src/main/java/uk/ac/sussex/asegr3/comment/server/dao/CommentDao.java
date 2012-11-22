package uk.ac.sussex.asegr3.comment.server.dao;


	import java.util.List;

import org.skife.jdbi.v2.sqlobject.Bind;

import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.CommentDTO;

	public interface CommentDao {

		  @SqlUpdate("insert into comment (fk_user_id, text, location_id, image) values ((select id from user where username=:username), :text, :location_id, :image)")
		  void insert(@Bind("username") String userId, @Bind("text") String text , @Bind("location_id") int locationID, @Bind("image") byte[] bytes );

		void insert(String testUsername, List<CommentDTO> asList); 

		
	}

