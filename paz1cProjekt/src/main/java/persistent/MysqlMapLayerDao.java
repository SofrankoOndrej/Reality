package persistent;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import entities.MapLayer;

public class MysqlMapLayerDao implements MapLayerDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlMapLayerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	@Override
	public void add(MapLayer mapLayer) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<MapLayer> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MapLayer save(MapLayer mapLayer) {
		// TODO Auto-generated method stub
		return null;
	}

}
