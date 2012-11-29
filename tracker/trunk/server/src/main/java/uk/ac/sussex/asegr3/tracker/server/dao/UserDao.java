package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;

public interface UserDao {

	@SqlQuery("select password from user where username=:username")
	String getPasswordForUser(@Bind("username") String username);

	@SqlQuery("select count(1) > 0 from user where username=:username")
	boolean exists(@Bind("username") String username);

	@SqlUpdate("insert into user(username, password) values (:username, :password)")
	void insert(@Bind("username") String username, @Bind("password") String pwHash);

}
