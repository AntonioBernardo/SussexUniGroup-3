package uk.ac.sussex.asegr3.tracker.server.dao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import uk.ac.sussex.asegr3.tracker.server.dao.mapper.UserDTOMapper;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;

@RegisterMapper(UserDTOMapper.class)
public interface UserDao {

	@SqlQuery("select password from user where username=:username")
	String getPasswordForUser(@Bind("username") String username);

	@SqlQuery("select count(1) > 0 from user where username=:username")
	boolean exists(@Bind("username") String username);
	
	@SqlUpdate("update user set lastloggindate = :timestamp where username = :username")
	void updateLastLoggin(@Bind("username") String username, @Bind("timestamp") long timestamp);

	@SqlUpdate("insert into user(username, password, name, surname, age, gender, about, interests, lastloggindate, signupdate) values (:email, :passwordHash, :name, :surname, :age, :genderId, :about, :interests, :lastLogginDate, :signupDate)")
	void insert(@BindBean UserDTO user, @Bind("passwordHash") String passwordHash);
	
	@SqlQuery("select username, password, name, surname, age, gender, about, interests, lastloggindate, signupdate from user where username = :username")
	UserDTO getUser(@Bind("username") String username, String requester);

}
