package persistent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import entities.Address;
import entities.Participant;
import entities.Property;
import entities.User;

public class MysqlParticipantDao implements ParticipantDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlParticipantDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public List<Participant> getAll(Property property) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Participant save(User user, Property property, Address address) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Participant add(Participant participant, Property property) {
		// TODO Auto-generated method stub
		return null;
	}

}
