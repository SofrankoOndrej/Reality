package util;

import java.util.List;

import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public class MapUtils {

	public static List<Tile> getTilesNumbersFromBoundingBox(Double[][] boundingBox, int zoomLevel) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String constructUrl(MapLayer mapLayer, Tile tile) {
		// create URL of map tile based on mapLayer basemap type
		if(mapLayer.getTileUrlFormat() == null) {
			// default fallback for tile url format
			StringBuilder sb = new StringBuilder();
			sb.append(mapLayer.getUrl());
			sb.append("/");
			sb.append(tile.getZoom());
			sb.append("/");
			sb.append(tile.getLongitude());
			sb.append("/");
			sb.append(tile.getLatitude());
			
			return sb.toString();	
		}else {
			// naformatuj podla formatovacieho retazca
			return null;
		}
		
	}

	public static boolean isTileDownloaded(Tile tile, String cacheFolderPath) {
		
		return false;
	}
	
	public static Image downloadTile(Tile tile, String cacheFolderPath) {

		
		return null;
	}

}
