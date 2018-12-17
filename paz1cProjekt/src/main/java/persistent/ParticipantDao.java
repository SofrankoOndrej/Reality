package persistent;

import java.util.List;

import entities.Address;
import entities.Participant;
import entities.Property;
import entities.User;

public interface ParticipantDao {
	
	Participant add(Participant participant, Property property);

	List<Participant> getAll(Property property);

	Participant save(User user, Property property, Address address);
}
