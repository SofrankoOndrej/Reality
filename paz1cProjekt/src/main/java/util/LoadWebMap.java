package util;

import java.util.List;

import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public class LoadWebMap implements Map {

	@Override
	public Image getTile(MapLayer mapLayer, Tile tile) {
		// sprav URL pola typu mapy
		String constructedUrl = MapUtils.constructUrl(mapLayer, tile);
		Image tileImage = new Image(constructedUrl);
		return tileImage;
	}

	@Override
	public Image loadMapLayer(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel) {
		// get tile numbers
		List<Tile> tiles = MapUtils.getTilesNumbersFromBoundingBox(boundingBox, zoomLevel);
		// download tiles
		for (Tile tile: tiles) {
			
			// construct image
			
			
		}



		return null;
	}

}
