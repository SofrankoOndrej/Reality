package util;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;

import entities.MapLayer;
import entities.Tile;
import entities.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import persistent.DaoFactory;
import persistent.TileDao;

public class LoadWebMap implements Map {

	private String tempPath;
	private MapLayer mapLayer;
	private User user;
	private int zoom;
	private TileDao tileDao = DaoFactory.INSTANCE.getTileDao();

	public LoadWebMap(User user, MapLayer mapLayer, int zoom) {
		this.mapLayer = mapLayer;
		this.user = user;
		this.zoom = zoom;
		this.tempPath = user.getCacheFolderPath();
	}

	@Override
	public Image getTile(Tile tile) {
		// check if tile is cached
		boolean isTileCached = tileDao.isTileCached(user, mapLayer, tile);
		Image tileImage = null;
		if (isTileCached) {
			// get tile cache location from database
			tile = tileDao.getFullTile(user,mapLayer,tile);
			// read tile from disk
			tileImage = TileUtils.readTileFromDisk(tile);
			return tileImage;
		} else {
			// sprav URL pola typu mapy
			String constructedUrl = MapUtils.constructUrl(mapLayer, tile);
			//System.out.println(constructedUrl);

			// url connection is used so I can peak into inputStream and get content type -
			// image file extension
			try {
				// download urlStream
				URL tileUrl = new URL(constructedUrl);
				InputStream tileInputStream = new BufferedInputStream(tileUrl.openStream()); // you can add buffer size

				// Tile.setFileFormat
				String fileExtension = URLConnection.guessContentTypeFromStream(tileInputStream);
				// validate that TILE is in image format
				String mimeType[] = fileExtension.split("/");
				if (mimeType[0].equals("image")) {
					tile.setFileFormat(mimeType[1]);
				} else {
					String message = "tile: " + constructedUrl + " is not an image." + " [" + fileExtension + "]";
					System.out.println(message);
				}

				tileImage = new Image(tileInputStream);
				Tile tileDownloaded = TileUtils.saveTile(tileImage, tile, user.getCacheFolderPath());
				if (!(tileDownloaded.getCachedLocation() == null)) {
					tileDao.save(tileDownloaded, user, mapLayer);
				} else {
					// TODO generate tileImage 404
				}
				return tileImage;

			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("tile not found: " + constructedUrl);
			}
		}
		// v pripade ak nastane vynimka
		return tileImage;
	}

	@Override
	public Image loadMapLayer(String boundingBox) {
		// get tile numbers
		List<Tile> tiles = MapUtils.getTilesFromBoundingBox(boundingBox, zoom);
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
			// is tile downloaded
			if (!TileUtils.isTileDownloaded(tile, tempPath)) {
				// save images
				String tileCachePath = tempPath + "/" + tile.getZoom() + "/" + tile.getLongitude() + "/"
						+ tile.getLatitude();
				File outputFile = new File(tileCachePath);

				Image tileImage = getTile(tile);

				BufferedImage bImage = SwingFXUtils.fromFXImage(tileImage, null);
				try {
					ImageIO.write(bImage, "png", outputFile);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
				tile.setCachedLocation(tileCachePath);

				tileDao.save(tile, user, mapLayer);
			}

			// tile's shift to canvas

			// construct image

		}
		return null;

	}

	@Override
	public List<Image> getTiles(String boundingBox) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tile> getListOfTiles(String boundingBox) {
		List<Tile> tiles = MapUtils.getTilesFromBoundingBox(boundingBox, zoom);

		return tiles;
	}

}
