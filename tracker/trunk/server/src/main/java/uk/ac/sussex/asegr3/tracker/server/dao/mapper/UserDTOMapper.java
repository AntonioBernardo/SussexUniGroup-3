package uk.ac.sussex.asegr3.tracker.server.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;

public class UserDTOMapper implements ResultSetMapper<UserDTO>{

	@Override
	public UserDTO map(int index, ResultSet r, StatementContext ctx) throws SQLException {
		String username = r.getString("username");
		String name = r.getString("name");
		String surname = r.getString("username");
		int age = r.getInt("age");
		UserDTO.Gender gender = UserDTO.Gender.valueOfById(r.getString("gender"));
		String about = r.getString("about");
		String interests = r.getString("interests");
		long lastLogginDate = r.getLong("lastLogginDate");
		long signupDate = r.getLong("signupDate");
		
		return new UserDTO(username, name, surname, age, gender, about, interests, lastLogginDate, signupDate);
	}

}
