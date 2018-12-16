package util;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.junit.jupiter.api.Test;

import entities.MapLayer;
import entities.Tile;
import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;

public class MapUtils {

	public static List<Tile> getTilesFromBoundingBox(String boundingBoxString, int zoomLevel) {
		double[] boundingBox = bBoxString2DoubleArray(boundingBoxString);

		double upperLeft[] = TileUtils.globe2mercator(boundingBox[0], boundingBox[1]);
		double bottomRight[] = TileUtils.globe2mercator(boundingBox[2], boundingBox[3]);

		int upperLeftTileNumbers[] = TileUtils.mercator2tile(upperLeft[0], upperLeft[1], zoomLevel);
		int upperBottomRightNumbers[] = TileUtils.mercator2tile(bottomRight[0], bottomRight[1], zoomLevel);

		List<Tile> tileList = new ArrayList<Tile>();
		// iterate through whole tile region
		for (int longitude = upperLeftTileNumbers[0]; longitude <= upperBottomRightNumbers[0]; longitude++) {
// vyries v ktorom smere treba iterovat
			for (int latitude = upperLeftTileNumbers[1]; latitude <= upperBottomRightNumbers[1]; latitude++) {
				// create tile
				Tile tileToAdd = new Tile();
				tileToAdd.setLongitude(longitude);
				tileToAdd.setLatitude(latitude);
				tileToAdd.setZoom(zoomLevel);
				tileList.add(tileToAdd);
			}
		}

		return tileList;
	}

	public static String constructUrl(MapLayer mapLayer, Tile tile) {
		// create URL of map tile based on mapLayer basemap type
		if (mapLayer.getTileUrlFormat() == null) {
			// default fallback for tile url format
			StringBuilder sb = new StringBuilder();
			sb.append(mapLayer.getMapServerUrl());
			sb.append(tile.getZoom());
			sb.append("/");
			sb.append(tile.getLatitude());
			sb.append("/");
			sb.append(tile.getLongitude());

			return sb.toString();
		} else {
			// naformatuj podla formatovacieho retazca
			return null;
		}

	}

	public static double[] bBoxString2DoubleArray(String bbox) {
		String[] coordinates = bbox.split(",");
		double[] bboxDoubleArray = new double[4];
		for (int i = 0; i < 4; i++) {
			bboxDoubleArray[i] = Double.parseDouble(coordinates[i]);
		}

		return bboxDoubleArray;
	}

	public static String bBoxDoubleArray2String(double[] bbox) {
		String bboxString;
		bboxString = Double.toString(bbox[0]) + ", " + Double.toString(bbox[1]) + ", " + Double.toString(bbox[2]) + ", "
				+ Double.toString(bbox[3]);

		return bboxString;
	}

	

}
