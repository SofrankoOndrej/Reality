package persistent;

import java.util.List;

import entities.Address;
import entities.Property;
import entities.User;

public interface PropertyDao {
	Property add(Property property, User user);

	List<Property> getAll(User user);

	Property save(Property property, User user, Address address);

	List<Property> getFromBbox(User user, String bbox);

	void delete(Property property);
}
