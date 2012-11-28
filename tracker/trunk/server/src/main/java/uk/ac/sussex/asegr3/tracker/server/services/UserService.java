package uk.ac.sussex.asegr3.tracker.server.services;

import uk.ac.sussex.asegr3.tracker.server.dao.UserDao;
import uk.ac.sussex.asegr3.tracker.server.services.authentication.AuthenticationService;

public class UserService {

	private final UserDao userDao;
	private final AuthenticationService authenticationService;
	
	public UserService(UserDao userDao, AuthenticationService authenticationService){
		this.userDao = userDao;
		this.authenticationService = authenticationService;
	}
	
	public void addNewUser(String username, String password) throws UserAlreadyExistsException {
		// first check that this user does not already exist
		
		if (userDao.exists(username)){
			throw new UserAlreadyExistsException("The user "+username+" already exists on the system. Please try a different username or login with the existing one");
		}
		
		String pwHash = authenticationService.computePwHash(password);
		
		userDao.insert(username, pwHash);
	}

}
