package util;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Tile;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

public class TileUtils {

	public static double[] globe2mercator(double longitude, double latitude) {
		double mercXY[] = new double[2];
		mercXY[0] = (longitude / 180 * 20037508.34);
		mercXY[1] = (20037508.34 / Math.PI * (Math.log(Math.tan(latitude * Math.PI / 360 + Math.PI / 4))));

		return mercXY;
	}

	public static int[] gloge2tile(double longitude, double latitude) {
		int tileXY[] = new int[2];
		double x = (longitude + 180) / 360;

		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));
		long mapSize = 2 ^ 18;
		tileXY[0] = (int) Math.floor(x * mapSize + 0.5);
		tileXY[1] = (int) Math.floor(y * mapSize + 0.5);
		return tileXY;
	}

//	%% prevod z mercator na suradnice
	public static double[] mercator2globe(double mercX, double mercY) {
		double globeXY[] = new double[2];
		globeXY[0] = (mercX / 20037508.34) * 180;
		globeXY[1] = (mercY / 20037508.34) * 180;
		globeXY[1] = 180 / Math.PI * (2 * Math.atan(Math.exp(globeXY[1] * Math.PI / 180)) - Math.PI / 2);

		return globeXY;
	}

	public static int[] mercator2pixel(double mercX, double mercY, int zoomLevel) {
		int[] pixelXY = new int[2];
//		% prevod na zemepisne suradnice: sirka, dlzka
		double longitude = (mercX / 20037508.34) * 180;
		double latitude = (mercY / 20037508.34) * 180;
		latitude = 180 / Math.PI * (2 * Math.atan(Math.exp(latitude * Math.PI / 180)) - Math.PI / 2);
//		% prevod zo zemepisnych suradnic na polohu v obdlzniku mercatoru
		double x = (longitude + 180) / 360;
		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));
		int mapSize = 256 * 2 ^ zoomLevel;
//		% vzdialenost v pixeloch od pociatku suradnicovej sustavy (lavy horny roh)
		pixelXY[0] = (int) Math.round(x * mapSize);
		pixelXY[1] = (int) Math.round(y * mapSize);

		return pixelXY;
	}

	public static int[] globe2pixel(double longitude, double latitude, int zoomLevel) {
		int[] pixelXY = new int[2];
//		% prevod zo zemepisnych suradnic na polohu v obdlzniku mercatoru
		double x = (longitude + 180) / 360;
		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));
		int mapSize = 256 * 2 ^ zoomLevel;
//		% vzdialenost v pixeloch od pociatku suradnicovej sustavy (lavy horny roh)
		pixelXY[0] = (int) Math.round(x * mapSize);
		pixelXY[1] = (int) Math.round(y * mapSize);

		return pixelXY;
	}

	/*
	 * returns geo location of mercator pixel at specified zoom level
	 */
	public static double[] pixel2globe(int x, int y, int zoom) {
		double globe[] = new double[2];
		// implement zoom level of map
		int mapSize = 256 * 2 ^ zoom;
		// mercator position to interval <0,1>
		double xDouble = 1.0 * x / mapSize;
		double yDouble = 1.0 * y / mapSize;

		// mercator position in interval <0,1>
		// longitude
		globe[0] = 360 * xDouble - 180;
		// latitude
		double eLat = Math.exp(4 * Math.PI * (0.5 - yDouble));
		globe[1] = 180 / Math.PI * Math.asin((eLat - 1) / (eLat + 1));

		return globe;
	}

	public static int[] mercator2tile(double mercatorX, double mercatorY, int zoomLevel) {
		int tileXY[] = new int[2];

		double longitude = (mercatorX / 20037508.34) * 180;
		double latitude = (mercatorY / 20037508.34) * 180;
		latitude = 180 / Math.PI * (2 * Math.atan(Math.exp(latitude * Math.PI / 180)) - Math.PI / 2);

		double x = (longitude + 180) / 360;
		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));

		double mapSize = Math.pow(2, zoomLevel);
		tileXY[0] = (int) Math.floor(x * mapSize);
		tileXY[1] = (int) Math.floor(y * mapSize);

		return tileXY;
	}

	public static int[] tile2pixel(Tile tile) {
		// TODO Auto-generated method stub
		return null;
	}

	public static Integer[] pixel(double latitude, double longitude) {
		Integer[] pixelXY = new Integer[2];
		// pixel v dlazdici
		double x = (longitude + 180) / 360;
		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));

		double mapSize = 2 ^ 18;
		int tileX = (int) Math.floor(x * mapSize + 0.5);
		int tileY = (int) Math.floor(y * mapSize + 0.5);
		pixelXY[0] = (int) Math.round((x * mapSize + 0.5) * 256) - tileX * 256;
		pixelXY[1] = (int) (mapSize * 256 - Math.round((y * mapSize + 0.5) * 256) - tileY * 256); // opacny smer

		return pixelXY;
	}

	public static Tile saveTile(Image image, Tile tile, String cacheFolderPath) {
		String constructedFolderPath = constructFolderPath(tile, cacheFolderPath);
		File folder = new File(constructedFolderPath);
		String constructedFilePath = constructFilePath(tile, cacheFolderPath);
		File file = new File(constructedFilePath);

		boolean directoriesCreated = folder.mkdirs() || folder.exists();
		boolean fileAlreadySaved = isTileDownloaded(tile, cacheFolderPath);
		// do if tile does NOT have cache location in database
		if (tile.getCachedLocation() == null) {
			tile.setCachedLocation(constructedFilePath);
		}
		// if the file is not saved -> write to file
		if (directoriesCreated && !fileAlreadySaved) {
			try {
				if (ImageIO.write(SwingFXUtils.fromFXImage(image, null), tile.getFileFormat(), file)) {
					tile.setCachedLocation(constructedFilePath);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(constructedFilePath);
		}

		return tile;

	}

	public static Image readTileFromDisk(Tile tile) {
		Image tileImage = new Image("file:///" + tile.getCachedLocation());
		return tileImage;
	}

	public static boolean isTileDownloaded(Tile tile, String cacheFolderPath) {
		String filePath = constructFilePath(tile, cacheFolderPath);
		File tileFile = new File(filePath);

		return tileFile.exists() && !tileFile.isDirectory();
	}

	public static String constructFolderPath(Tile tile, String cacheFolderPath) {
		StringBuilder sb = new StringBuilder();
		sb.append(cacheFolderPath);
		if (!cacheFolderPath.endsWith("\\")) {
			sb.append("\\");
		}
		sb.append(tile.getZoom());
		sb.append("\\");
		sb.append(tile.getLongitude());
		sb.append("\\");

		return sb.toString();

	}

	public static String constructFilePath(Tile tile, String cacheFolderPath) {
		StringBuilder sb = new StringBuilder();
		sb.append(constructFolderPath(tile, cacheFolderPath));
		if (!sb.toString().endsWith("\\")) {
			sb.append("\\");
		}
		sb.append(tile.getLatitude());
		sb.append(".");
		sb.append(tile.getFileFormat());

		return sb.toString();
	}

}
