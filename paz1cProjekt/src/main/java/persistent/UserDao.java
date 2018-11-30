package persistent;

import java.util.List;

import entities.User;

public interface UserDao {

	void add(User user);

	List<User> getAll();

	User save(User user);

	/**
	 * Method used for validation of user
	 * 
	 * @param username
	 * @return
	 */
	User getByUsername(String username);
	
	/**
	 * Method used to return full user after validation
	 * @param username
	 * @return
	 */
	User getByUsernameFull(String username);

}
