package util;

import java.net.URL;

import entities.Tile;

public class Utils {

	/* Returns true if url is valid */
	public static boolean isValidURL(String url) {
		/* Try creating a valid URL */
		try {
			new URL(url).toURI();
			return true;
		}

		// If there was an Exception
		// while creating URL object
		catch (Exception e) {
			return false;
		}
	}

	public static String parseUrl2UrlMapBaseFormat(String url) {
		String[] tokens = url.split("/");
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < tokens.length - 3; i++) {
			sb.append(tokens[i]);
			sb.append("/");
		}

		return sb.toString();
	}

	public static double[] bBoxString2DoubleArray(String bbox) {
		String[] coordinates = bbox.split(";");
		double[] bboxDoubleArray = new double[4];
		for (int i = 0; i < 4; i++) {
			bboxDoubleArray[i] = Double.parseDouble(coordinates[i]);
		}

		return bboxDoubleArray;
	}

	public static String bBoxDoubleArray2String(double[] bbox) {
		String bboxString;
		bboxString = Double.toString(bbox[0]) + "; " + Double.toString(bbox[1]) + "; " + Double.toString(bbox[2]) + "; "
				+ Double.toString(bbox[3]);

		return bboxString;
	}
}
