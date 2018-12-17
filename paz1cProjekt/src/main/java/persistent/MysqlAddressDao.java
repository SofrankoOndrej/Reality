package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import entities.Address;

public class MysqlAddressDao implements AddressDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlAddressDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Address add(Address address) {
		if (address == null)
			return null;
		if (address.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("addresses");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("state", "city", "street", "number", "zipCode","longitude","latitude");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("state", address.getState());
			hodnoty.put("city", address.getCity());
			hodnoty.put("street", address.getStreet());
			hodnoty.put("number", address.getNumber());
			hodnoty.put("zipCode", address.getZipCode());
			hodnoty.put("longitude", address.getLongitude());
			hodnoty.put("latitude", address.getLatitude());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			address.setId(id);
		}
		return address;
	}

	@Override
	public List<Address> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address save(Address address) {
		if (address == null)
			return null;
		if (address.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("addresses");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("state", "city", "street", "number", "zipCode","longitude","latitude");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("state", address.getState());
			hodnoty.put("city", address.getCity());
			hodnoty.put("street", address.getStreet());
			hodnoty.put("number", address.getNumber());
			hodnoty.put("zipCode", address.getZipCode());
			hodnoty.put("longitude", address.getLongitude());
			hodnoty.put("latitude", address.getLatitude());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			address.setId(id);
		} else {
			// UPDATE
			String sql = "UPDATE adresses SET "
					+ "state = ? city = ? street = ? number = ? zipCode = ? longitude = ? latitude = ?"
					+ " WHERE id = ? ";
			jdbcTemplate.update(sql, address.getState(), address.getCity(), address.getStreet(), address.getNumber(),
					address.getZipCode(), address.getLongitude(), address.getLatitude(), address.getId());
		}
		return address;
	}

	@Override
	public Address getById(int id) {
		String sql = "SELECT * FROM addresses " + "WHERE id = " + id;
		Address address;
		try {
			address = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Address.class));
			return address;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			return null;
		}
	}

}
