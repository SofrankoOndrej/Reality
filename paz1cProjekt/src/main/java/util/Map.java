package util;

import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public interface Map {

	Image getTile(MapLayer mapLayer, Tile tile);

	Image loadMapLayer(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel);

}
