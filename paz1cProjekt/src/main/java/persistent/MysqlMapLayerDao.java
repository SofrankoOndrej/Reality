package persistent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import entities.MapLayer;
import entities.User;

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
	public MapLayer save(MapLayer mapLayer, User user) {
		if (mapLayer == null)
			return null;
		if (mapLayer.getId() == null) {
			// CREATE
			SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
			simpleJdbcInsert.withTableName("map_layers");
			simpleJdbcInsert.usingGeneratedKeyColumns("id");

			simpleJdbcInsert.usingColumns("name", "mapServerUrl", "sampleTileUrl", "users_id");
			Map<String, Object> hodnoty = new HashMap<>();
			hodnoty.put("name", mapLayer.getName());
			hodnoty.put("sampleTileUrl", mapLayer.getSampleTileUrl());
			hodnoty.put("mapServerUrl", mapLayer.getUrl());
			hodnoty.put("users_id", user.getId());

			// hodnoty.put("users_id", user.getId());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			mapLayer.setId(id);
		} else {
			// UPDATE
			String sql = "UPDATE map_layers SET " + "name = ? mapServerUrl = ? sampleTileUrl = ? " + "WHERE id = ? ";
			jdbcTemplate.update(sql, mapLayer.getName(), mapLayer.getUrl(), mapLayer.getSampleTileUrl(),
					mapLayer.getId());
		}
		return mapLayer;
	}

}
