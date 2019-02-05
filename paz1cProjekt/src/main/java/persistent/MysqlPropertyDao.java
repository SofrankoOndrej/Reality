package persistent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import entities.Address;
import entities.Property;
import entities.User;
import util.MapUtils;

public class MysqlPropertyDao implements PropertyDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlPropertyDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Property add(Property property, User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Property> getAll(User user) {
		String sql = "SELECT id FROM properties WHERE users_id = " + user.getId();
		List<Long> propertyId = jdbcTemplate.queryForList(sql, Long.class);
		// get mapLayerById
		List<Property> properties = new ArrayList<>();
		if (propertyId != null) {
			for (int i = 0; i < propertyId.size(); i++) {
				properties.add(getById(propertyId.get(i)));
			}
		}
		return properties;
	}

	@Override
	public Property save(Property property, User user, Address address) {
		if (property == null)
			return null;
		if (property.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("properties");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			// TODO v databaze zmenit "address_id" na "addresses_id"
			simpleJdbcInsert.usingColumns("name", "type", "rating", "users_id", "address_id", "description", "url",
					"price");
			Map<String, Object> values = new HashMap<>();
			values.put("name", property.getName());
			values.put("type", property.getType());
			values.put("rating", property.getRating());
			values.put("users_id", user.getId());
			values.put("address_id", address.getId());
			values.put("description", property.getDescription());
			values.put("url", property.getUrl());
			values.put("price", property.getPrice());

			Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			property.setId(id);
		} else {
			// TODO UPDATE
			String sql = "UPDATE properties SET "
					+ "name = ?, type = ?, rating = ?, users_id = ?, address_id = ?, description = ?, url = ?, price = ? "
					+ " WHERE id = ? ";
			jdbcTemplate.update(sql, property.getName(), property.getType(), property.getRating(), user.getId(),
					address.getId(), property.getDescription(), property.getUrl(), property.getPrice(),
					property.getId());
		}
		return property;
	}

	@Override
	public Property getById(Long propertyId) {
		String sql = "SELECT * FROM properties WHERE id = " + propertyId;
		Property property = new Property();
		try {
			property = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Property.class));
			return property;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			System.out.println("No properties to get.");
			return null;
		}
	}

	@Override
	public List<Property> getFromBbox(User user, String bbox) {
		List<Property> propertyList;
		AddressDao addressDao = DaoFactory.INSTANCE.getAddressDao();
		double[] bboxDoubleArray = MapUtils.bBoxString2DoubleArray(bbox);
		// TODO sprav select PROPERTIES
		String sql = "SELECT p.* FROM properties p INNER JOIN addresses a ON p.address_id = a.id ";// + " WHERE " +
																									// "a.zipCode =
																									// 04001; ";
		Property property = new Property();
		try {
			propertyList = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Property.class));

			for (int i = 0; i < propertyList.size(); i++) {
				Property propertyWithAddress = propertyList.get(i);
				// TODO get address of property
//				String addressSql = "SELECT * FROM addresses WHERE addresses_id = " + propertyWithAddress.getAddress_id().toString();
				Address address = addressDao.getById(propertyWithAddress.getAddress_id());
				propertyWithAddress.setAddress(address);
				propertyList.set(i, propertyWithAddress);
			}
			return propertyList;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			System.out.println("No properties to get.");
			return null;
		}

		// TODO select adresses and put them into property

	}

	@Override
	public void delete(Property property) {
		// TODO Auto-generated method stub
		jdbcTemplate.update("DELETE FROM properties WHERE id = ?", property.getId());

	}
}
