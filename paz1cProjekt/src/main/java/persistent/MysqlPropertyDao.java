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
		List<Integer> propertyId = jdbcTemplate.queryForList(sql, Integer.class);
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
			simpleJdbcInsert.usingColumns("name", "type", "rating", "users_id","address_id");
			Map<String, Object> values = new HashMap<>();
			values.put("name", property.getName());
			values.put("type", property.getType());
			values.put("rating", property.getRating());
			values.put("users_id", user.getId());
			values.put("address_id", address.getId());

			Long id = simpleJdbcInsert.executeAndReturnKey(values).longValue();
			property.setId(id);
		} else {
			// TODO UPDATE
//			String sql = "UPDATE map_layers SET " + "name = ? mapServerUrl = ? sampleTileUrl = ? " + " WHERE id = ? ";
//			jdbcTemplate.update(sql, mapLayer.getName(), mapLayer.getMapServerUrl(), mapLayer.getSampleTileUrl(),
//					mapLayer.getId());
		}
		return property;
	}

	private Property getById(Integer propertyId) {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Property property) {
		// TODO Auto-generated method stub
		
	}
}
