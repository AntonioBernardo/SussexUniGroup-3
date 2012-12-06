package uk.ac.sussex.asegr3.tracker.server.services;

import uk.ac.sussex.asegr3.tracker.server.Clock;
import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.domainmodel.UserDTO;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

public class UserService {

	private final UserDao userDao;
	private final AuthenticationService authenticationService;
	private final Clock clock;
	
	public UserService(UserDao userDao, AuthenticationService authenticationService, Clock clock){
		this.userDao = userDao;
		this.authenticationService = authenticationService;
		this.clock = clock;
	}
	
	public void addNewUser(String email, String password, String name, String surname, int age, String gender, String about, String interests) throws UserAlreadyExistsException {
		// first check that this user does not already exist
		
		if (userDao.exists(email)){
			throw new UserAlreadyExistsException("The user "+email+" already exists on the system. Please try a different username or login with the existing one");
		}
		
		String pwHash = authenticationService.computePwHash(password);
		long currentTime = clock.getCurrentTime();
		UserDTO newUser = new UserDTO(email, name, surname, age, UserDTO.Gender.valueOf(gender.toUpperCase().trim()), about, interests, currentTime, currentTime);
		userDao.insert(newUser, pwHash);
	}
	
	public UserDTO getUserProfile(String email, String requester) throws UserNotFoundOrPermissionedException{
		UserDTO user = userDao.getUser(email, requester);
		
		if (user == null){
			throw new UserNotFoundOrPermissionedException("The user: "+email+" is either not found or you are not permissioned to view this profile");
		}
		
		return user;
	}

}
