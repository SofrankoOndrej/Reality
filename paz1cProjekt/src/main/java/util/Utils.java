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
	
	public static boolean downloadTile(Tile tile, String cacheFolderPath) {
		
		return false;
	}
}
