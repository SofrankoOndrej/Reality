package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
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
	public Tile save(Tile tile, User user, MapLayer mapLayer) {

		if (tile == null)
			return null;
		if (tile.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("tiles");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("longitude", "latitude", "zoom", "map_layer_id", "map_layer_users_id",
					"thumbnail", "cachedLocation", "fileFormat");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("longitude", tile.getLongitude());
			hodnoty.put("latitude", tile.getLatitude());
			hodnoty.put("zoom", tile.getZoom());
			hodnoty.put("map_layer_id", mapLayer.getId());
			hodnoty.put("map_layer_users_id", user.getId());
			hodnoty.put("thumbnail", tile.getThumbnail());
			hodnoty.put("cachedLocation", tile.getCachedLocation());
			hodnoty.put("fileFormat", tile.getFileFormat());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			tile.setId(id);
		} else {
			String sql = "UPDATE tiles SET " + "thumbnail = ?, cachedLocation = ?, fileFormat = ? " + "WHERE id = ? ;";
			jdbcTemplate.update(sql, tile.getThumbnail(), tile.getCachedLocation(), tile.getFileFormat(), tile.getId());
		}
		return tile;
	}

	@Override
	public boolean isTileCached(User user, MapLayer mapLayer, Tile tile) {
		String sql = "SELECT EXISTS ( "
				+ "SELECT id, longitude, latitude, zoom, thumbnail, cachedLocation, fileFormat " 
				+ "FROM tiles "
				+ "WHERE map_layer_users_id = " + user.getId() + " AND "
				+ "map_layer_id = " + mapLayer.getId() + " AND "
				+ "tiles.zoom = " + tile.getZoom() + " AND "
				+ "tiles.latitude = " + tile.getLatitude() + " AND "
				+ "tiles.longitude = " + tile.getLongitude() +
				" );";
		boolean exists = jdbcTemplate.queryForObject(sql, boolean.class);
		return exists;
	}

	@Override
	public String getTileCacheLocation(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Tile getFullTile(User user, MapLayer mapLayer, Tile tile) {
		String sql = "SELECT id, longitude, latitude, zoom, thumbnail, cachedLocation, fileFormat " + "FROM tiles "
				+ "WHERE map_layer_users_id = " + user.getId() + " AND "
				+ "map_layer_id = " + mapLayer.getId() + " AND "
				+ "tiles.zoom = " + tile.getZoom() + " AND "
				+ "tiles.latitude = " + tile.getLatitude() + " AND "
				+ "tiles.longitude = " + tile.getLongitude();
		Tile cachedTile;
		try {
			cachedTile = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Tile.class));
			return cachedTile;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			return null;
		}
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
