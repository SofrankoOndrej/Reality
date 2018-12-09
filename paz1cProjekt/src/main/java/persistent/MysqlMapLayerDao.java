package persistent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import entities.MapLayer;
import entities.User;
import javafx.scene.image.Image;

public class MysqlMapLayerDao implements MapLayerDao {

	private JdbcTemplate jdbcTemplate;

	public MysqlMapLayerDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void add(MapLayer mapLayer) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public List<MapLayer> getAll(User user) {
//		List<MapLayer> mapLayers = new ArrayList<MapLayer>();
//		String sql = "SELECT * FROM mapLayers WHERE users_id = " + user.getId(); 
//		mapLayers = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(mapLayers.class));
//		return mapLayers;
//		
//	}

	@Override
	public List<MapLayer> getAll(User user) {
		String sql = "SELECT id FROM map_layers WHERE users_id = " + user.getId();
		List<Integer> mapLayerId = jdbcTemplate.queryForList(sql, Integer.class);
		// get mapLayerById
		List<MapLayer> mapLayers = new ArrayList<>();
		if (mapLayerId != null) {
			for (int i = 0; i < mapLayerId.size(); i++) {
				mapLayers.add(getById(mapLayerId.get(i)));
			}
		}
		return mapLayers;
		// List<MapLayer> mapLayer = jdbcTemplate.query(sql, new
		// ResultSetExtractor<List<MapLayer>>() {
//
//			@Override
//			public List<MapLayer> extractData(ResultSet rs) throws SQLException, DataAccessException {
//				List<MapLayer> mapLayers = new ArrayList<>();
//				MapLayer mapLayer = null;
//				while (rs.next()) {
//					Long id = rs.getLong("id");
//					if (mapLayer == null || mapLayer.getId() != id) {
//						mapLayer = new MapLayer();
//						mapLayer.setId(rs.getLong("id"));
//						mapLayer.setName(rs.getString("name"));
//						mapLayer.setUrl(rs.getString("mapServerUrl"));
//						mapLayer.setSampleTileUrl(rs.getString("sampleTileUrl"));
//						mapLayer.setCacheFolderPath(rs.getString("cacheFolderPath"));
//						mapLayer.setTileUrlFormat(rs.getString("tileUrlFormat"));
//						mapLayers.add(mapLayer);
//					}
//				}
//				return mapLayers;
//			}
//		});
//
//		return mapLayer;

	}

	private MapLayer getById(Integer mapLayerId) {
		String sql = "SELECT * FROM map_layers WHERE id = " + mapLayerId;
		MapLayer mapLayer = new MapLayer();
		try {
			mapLayer = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(MapLayer.class));
			return mapLayer;
		} catch (EmptyResultDataAccessException e) {
			// e.printStackTrace();
			return null;
		}
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
			hodnoty.put("mapServerUrl", mapLayer.getMapServerUrl());
			hodnoty.put("users_id", user.getId());

			// hodnoty.put("users_id", user.getId());

			Long id = simpleJdbcInsert.executeAndReturnKey(hodnoty).longValue();
			mapLayer.setId(id);
		} else {
			// UPDATE
			String sql = "UPDATE map_layers SET " + "name = ? mapServerUrl = ? sampleTileUrl = ? " + "WHERE id = ? ";
			jdbcTemplate.update(sql, mapLayer.getName(), mapLayer.getMapServerUrl(), mapLayer.getSampleTileUrl(),
					mapLayer.getId());
		}
		return mapLayer;
	}

	@Override
	public Image getImage(MapLayer mapLayer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Image getThumbnail(MapLayer mapLayer) {
		// TODO Auto-generated method stub
		return null;
	}

}
