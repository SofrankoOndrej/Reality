package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
			//CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("users");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");
			
			simpleJdbcInsert.usingColumns("name", "surname", "email", "username", "password");
			Map<String,Object> hodnoty = new HashMap<>();
			hodnoty.put("name",user.getName());
			hodnoty.put("surname",user.getSurname());
			hodnoty.put("email",user.getEmail());
			hodnoty.put("username",user.getUsername());
			hodnoty.put("password",user.getPassword());
			
			
			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			user.setId(id);
		} else { 
			//UPDATE
			String sql = "UPDATE workshop SET " 
					+ "name = ? surname = ? " 
					+ "email = ? username = ? "
					+ "password = ? "
					+ "WHERE id = ? ";
			jdbcTemplate.update(sql,
					user.getName(), user.getSurname(),user.getEmail(),user.getUsername(),
					user.getPassword(),user.getId());
		}
		return user;

	}

	@Override
	public boolean verify(User user, String passwordHash) {
		// TODO Auto-generated method stub
		return false;
	}

}
