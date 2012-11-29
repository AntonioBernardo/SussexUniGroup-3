package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface CommentDao {

	@SqlUpdate("insert into comment (fk_user_id, text, location_id, image, timestamp_added) values ((select id from user where username=:username), :text, :location_id, :image, :timestamp)")
	void insert(@Bind("username") String userId, @Bind("text") String text,
			@Bind("location_id") int locationID, @Bind("image") byte[] bytes,
			@Bind("timestamp") long timestamp);

}
