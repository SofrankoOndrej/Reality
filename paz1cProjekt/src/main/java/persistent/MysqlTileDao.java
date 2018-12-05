package persistent;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import entities.MapLayer;
import entities.Tile;

public class MysqlTileDao implements TileDao {
	
	private JdbcTemplate jdbcTemplate;

	public MysqlTileDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(Tile tile) {
		// TODO Auto-generated method stub

	}

	@Override
	public Tile save(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isTileCached(Tile tile) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTileCacheLocation(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tile getFullTile(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tile> getAll(MapLayer mapLayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tile> getAll(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

}
