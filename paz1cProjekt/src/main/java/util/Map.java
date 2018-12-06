package util;

import java.util.List;
import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public interface Map {

	Image getTile(MapLayer mapLayer, Tile tile);
	
	List<Image> getTiles(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel);

	Image loadMapLayer(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel);

}
