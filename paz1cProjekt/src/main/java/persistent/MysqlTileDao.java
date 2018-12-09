package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import entities.MapLayer;
import entities.Tile;
import entities.User;

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
	public Tile save(Tile tile,User user,MapLayer mapLayer) {
		
		if (tile == null)
			return null;
		if (tile.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("tiles");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("longitude", "latitude", "zoom", "map_layer_id", "map_layer_users_id",
					"map_layer_users_id", "thumbnail", "cachedLocation");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("longitude", tile.getLongitude());
			hodnoty.put("latitude", tile.getLatitude());
			hodnoty.put("zoom", tile.getZoom());
			hodnoty.put("map_layer_id", mapLayer.getId());
			hodnoty.put("map_layer_users_id", user.getId());
			hodnoty.put("thumbnail", tile.getThumbnail());
			hodnoty.put("cachedLocation", tile.getCachedLocation());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			tile.setId(id);
		} else {
			String sql = "UPDATE tiles SET " + "thumbnail = ?, cachedLocation = ? " + "WHERE id = ? ;";
			jdbcTemplate.update(sql, tile.getThumbnail(), tile.getCachedLocation(), tile.getId());
		}
		return tile;
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

	@Override
	public Tile save(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

}
