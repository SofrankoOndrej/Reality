package persistent;

import java.util.List;

import entities.MapLayer;
import entities.User;

public interface MapLayerDao {
	
	void add(MapLayer mapLayer);

	List<MapLayer> getAll();
	
	MapLayer save(MapLayer mapLayer);
}
