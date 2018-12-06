package util;

import java.io.File;
import java.io.IOException;
import java.util.List;

import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public class LoadWebMap implements Map {

	private String tempPath;
	
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
		if (mapLayer.getCacheFolderPath() == null) {
			// set default cache folder path
			try {
				File tempFile = File.createTempFile("tempFile", ".tmp");
				// create default temp folder for mapLayer
				File tempPathParent = new File(tempFile.getParentFile().getAbsolutePath() + mapLayer.getName());
				if (!tempPathParent.exists()) {
					tempPathParent.mkdirs();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		for (Tile tile : tiles) {
			// save images
	
			
			// construct image

		}
		return null;
	}

	@Override
	public List<Image> getTiles(MapLayer mapLayer, Double[][] boundingBox, int zoomLevel) {
		// TODO Auto-generated method stub
		return null;
	}

}
