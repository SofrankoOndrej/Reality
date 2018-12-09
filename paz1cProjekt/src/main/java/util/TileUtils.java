package util;

public class TileUtils {

	public static double[] globe2mercator(double longitude, double latitude) {
		double mercXY[] = new double[2];
		mercXY[0] = (longitude / 180 * 20037508.34);
		mercXY[1] = (20037508.34 / Math.PI * (Math.log(Math.tan(latitude * Math.PI / 360 + Math.PI / 4))));

		return mercXY;
	}

	public static Integer[] gloge2tile(double longitude, double latitude) {
		Integer tileXY[] = new Integer[2];
		double x = (longitude + 180) / 360;

		double sinLatitude = Math.sin(latitude * Math.PI / 180);
		double y = (0.5 - Math.log((1 + sinLatitude) / (1 - sinLatitude)) / (4 * Math.PI));
		long mapSize = 2 ^ 18;
		tileXY[0] = (int) Math.floor(x * mapSize + 0.5);
		tileXY[1] = (int) Math.floor(y * mapSize + 0.5);
		return tileXY;
	}

//	%% prevod z mercator na suradnice
	public static Double[] mercator2globe(Double mercX, Double mercY) {
		Double globeXY[] = new Double[2];
		globeXY[0] = (mercX / 20037508.34) * 180;
		globeXY[1] = (mercY / 20037508.34) * 180;
		globeXY[1] = 180 / Math.PI * (2 * Math.atan(Math.exp(globeXY[1] * Math.PI / 180)) - Math.PI / 2);

		return globeXY;
	}
	
	

	public static Integer[] mercator2pixel(Double mercX, Double mercY, int zoomLevel) {
		Integer[] pixelXY = new Integer[2];
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

}
