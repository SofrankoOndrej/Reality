package persistent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import entities.Address;

public class MysqlAddressDao implements AddressDao{

	
	private JdbcTemplate jdbcTemplate;

	public MysqlAddressDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Address add(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Address> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address save(Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

}
