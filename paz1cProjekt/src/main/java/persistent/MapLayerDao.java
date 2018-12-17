package persistent;

import java.util.List;

import entities.MapLayer;
import entities.User;
import javafx.scene.image.Image;

public interface MapLayerDao {

	MapLayer add(MapLayer mapLayer, User user);

	List<MapLayer> getAll(User user);

	MapLayer save(MapLayer mapLayer, User user);

	Image getThumbnail(MapLayer mapLayer);
	
	boolean delete(MapLayer mapLayer);
}
