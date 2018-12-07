package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import entities.User;

public class MysqlUserDao implements UserDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlUserDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(User user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User save(User user) {
		if (user == null)
			return null;
		if (user.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("users");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("name", "surname", "email", "username", "password");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("name", user.getName());
			hodnoty.put("surname", user.getSurname());
			hodnoty.put("email", user.getEmail());
			hodnoty.put("username", user.getUsername());
			hodnoty.put("password", user.getPassword());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			user.setId(id);
		} else {
			// UPDATE bez hesla
			if (user.getPassword() == null) {
				String sql = "UPDATE users SET " + "name = ?, surname = ?, " + "email = ?, username = ?, "
						+ "cacheFolderPath = ? " + "WHERE id = ? ;";
				jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getEmail(), user.getUsername(),
						user.getCacheFolderPath(), user.getId());

			} else { // UPDATE s heslom
				String sql = "UPDATE users SET " + "name = ?, surname = ?, " + "email = ?, username = ?, "
						+ "password = ?, cacheFolderPath = ? " + "WHERE id = ? ;";
				jdbcTemplate.update(sql, user.getName(), user.getSurname(), user.getEmail(), user.getUsername(),
						user.getPassword(), user.getCacheFolderPath(), user.getId());
			}
		}
		return user;

	}

	/**
	 * returns user with only USERNAME and PWHASH
	 */
	@Override
	public User getByUsername(String username) {
		String sql = "SELECT users.username, users.password " + "FROM users " + "WHERE users.username = '" + username
				+ "'";
		User user;
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
			return user;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			return null;
		}
	}

	/**
	 * returns full USER
	 */
	@Override
	public User getByUsernameFull(String username) {
		String sql = "SELECT users.id, users.name, users.surname, users.email, users.username, users.password "
				+ "FROM users " + "WHERE users.username = '" + username + "'";
		User user;
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(User.class));
			return user;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			return null;
		}
	}
}
