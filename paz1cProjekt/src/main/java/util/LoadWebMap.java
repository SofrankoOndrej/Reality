package util;

import entities.MapLayer;
import javafx.scene.image.Image;

public class LoadWebMap implements Map {

	@Override
	public Image getTile(MapLayer mapLayer, int[][] tileNumbers, int zoomLevel) {
		// sprav URL pola typu mapy
		String constructedUrl = MapUtils.constructUrl(mapLayer, tileNumbers, zoomLevel);
		Image tileImage = new Image(constructedUrl);
		return tileImage;
	}

	@Override
	public Image loadMapLayer(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel) {
		// get tile numbers
		int[][] tiles = MapUtils.getTilesNumbersFromBoundingBox(boundingBox);
		// download tiles
		for (int row = 0; row < tiles.length; row++) {
			for (int col = 0; col < tiles[row].length; col++) {
				// construct image

			}
		}

//		for (int row = 0; row < array1.length; row++) {
//	    for (int col = 0; col < array1[row].length; col++) {
//	        array2[row][col] = array1[row][col];
//	    }
//	}

		return null;
	}

}
