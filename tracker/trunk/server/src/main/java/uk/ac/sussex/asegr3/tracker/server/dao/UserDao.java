package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;

public interface UserDao {

	@SqlQuery("select password from user where username=:username")
	String getPasswordForUser(@Bind("username") String username);

}
