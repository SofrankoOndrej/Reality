package persistent;

import java.util.List;

import entities.MapLayer;
import entities.User;
import javafx.scene.image.Image;

public interface MapLayerDao {

	void add(MapLayer mapLayer);

	List<MapLayer> getAll(User user);

	MapLayer save(MapLayer mapLayer, User user);
	
	Image getImage(MapLayer mapLayer);
	
	Image getThumbnail(MapLayer mapLayer);
}
