package util;

import java.util.List;
import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public interface Map {

	Image getTile(Tile tile);
	
	List<Image> getTiles(String boundingBox);
	
	List<Tile> getListOfTiles(String boundingBox);

	Image loadMapLayer(String boundingBox);

}
