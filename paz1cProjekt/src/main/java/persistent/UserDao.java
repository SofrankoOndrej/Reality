package persistent;

import java.util.List;

import entities.User;

public interface UserDao {

	void add(User user);

	List<User> getAll();

	User save(User user);

	boolean verify(User user, String passwordHash);

	User getByUsername(String username);

}
