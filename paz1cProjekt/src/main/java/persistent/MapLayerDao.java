package persistent;

import java.util.List;

import entities.MapLayer;

public interface MapLayerDao {
	
	void add(MapLayer mapLayer);

	List<MapLayer> getAll();
	
	MapLayer save(MapLayer mapLayer);
}
