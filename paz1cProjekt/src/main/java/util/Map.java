package util;

import entities.MapLayer;
import javafx.scene.image.Image;

public interface Map {

	Image getTile(MapLayer mapLayer, int[][] tileNumbers, int zoomLevel);

	Image loadMapLayer(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel);

}
