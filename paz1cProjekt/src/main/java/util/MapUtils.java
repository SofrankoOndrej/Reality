package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entities.MapLayer;
import entities.Tile;
import javafx.scene.image.Image;

public class MapUtils {

	public static List<Tile> getTilesFromBoundingBox(String boundingBoxString, int zoomLevel) {
		double[] boundingBox = Utils.bBoxString2DoubleArray(boundingBoxString);

		double upperLeft[] = TileUtils.globe2mercator(boundingBox[0], boundingBox[1]);
		double bottomRight[] = TileUtils.globe2mercator(boundingBox[2], boundingBox[3]);

		int upperLeftTileNumbers[] = TileUtils.mercator2tile(upperLeft[0], upperLeft[1], zoomLevel);
		int upperBottomRightNumbers[] = TileUtils.mercator2tile(bottomRight[0], bottomRight[1], zoomLevel);

		List<Tile> tileList = new ArrayList<Tile>();
		// iterate through whole tile region
		for (int longitude = upperLeftTileNumbers[0]; longitude <= upperBottomRightNumbers[0]; longitude++) {
// vyries v ktorom smere treba iterovat
			for (int latitude = upperLeftTileNumbers[1]; latitude > upperBottomRightNumbers[1]; latitude++) {
				// create tile
				Tile tileToAdd = new Tile();
				tileToAdd.setLongitude(longitude);
				tileToAdd.setLatitude(latitude);
				tileToAdd.setZoom(zoomLevel);
				tileList.add(tileToAdd);
			}
		}

		return null;
	}

	public static String constructUrl(MapLayer mapLayer, Tile tile) {
		// create URL of map tile based on mapLayer basemap type
		if (mapLayer.getTileUrlFormat() == null) {
			// default fallback for tile url format
			StringBuilder sb = new StringBuilder();
			sb.append(mapLayer.getMapServerUrl());
			sb.append("/");
			sb.append(tile.getZoom());
			sb.append("/");
			sb.append(tile.getLongitude());
			sb.append("/");
			sb.append(tile.getLatitude());

			return sb.toString();
		} else {
			// naformatuj podla formatovacieho retazca
			return null;
		}

	}

	public static boolean isTileDownloaded(Tile tile, String cacheFolderPath) {
		String folderPath = cacheFolderPath + tile.getZoom() + "/" + tile.getLongitude() + "/";
		boolean exists = new File(folderPath, Integer.toString(tile.getLatitude())).exists();
		return exists;
	}

	public static Image downloadTile(Tile tile, String cacheFolderPath) {

		return null;
	}

}
