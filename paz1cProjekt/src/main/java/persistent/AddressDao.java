package persistent;

import java.util.List;

import entities.Address;

public interface AddressDao {
	Address add(Address address);

	List<Address> getAll();

	Address save(Address address);
	
	Address getById(int id);
}
