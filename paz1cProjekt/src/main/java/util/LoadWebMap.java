package util;

import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
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
import persistent.MysqlTileDao;
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
		TileDao tileDao = DaoFactory.INSTANCE.getTileDao();
		// sprav URL pola typu mapy
			//tileDao.isTileCached(tile);
		String constructedUrl = MapUtils.constructUrl(mapLayer, tile);
		System.out.println(constructedUrl);

		// download urlStream
		URL tileUrl = new URL(constructedUrl);
		URLConnection urlConnection = new URLConnection(tileUrl) {
			
			@Override
			public void connect() throws IOException {
				// TODO Auto-generated method stub
				
			}
		};
		tileUrl.openConnection();
		
		InputStream inputStream = urlConnection.getInputStream();
		
		
		int pushbackLimit = 100;
		InputStream urlStream = tileUrl.openStream();
		
		String fileExtension = guessContentTypeFromStream(urlStream ); 
		// Tile.setFileFormat
		tile.setFileFormat(fileExtension);

		Image tileImage = new Image(constructedUrl);
		
		
		Tile tileDownloaded = TileUtils.saveTile(tileImage, tile, user.getCacheFolderPath());
		if(!tileDownloaded.getCachedLocation().isEmpty()) {
			tileDao.save(tileDownloaded, user, mapLayer);
		}else {
			// generate tileImage 404
		}
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
			if (!MapUtils.isTileDownloaded(tile, tempPath)) {
				// save images
				String tileCachePath = tempPath + "/" +  tile.getZoom() + "/" + tile.getLongitude() + "/" + tile.getLatitude();
				File outputFile = new File(tileCachePath);
				 
				Image tileImage = getTile(tile);
				
				BufferedImage bImage = SwingFXUtils.fromFXImage(tileImage, null);
				try {
				      ImageIO.write(bImage, "png", outputFile);
				    } catch (IOException e) {
				      throw new RuntimeException(e);
				    }
				tile.setCachedLocation(tileCachePath);
								
				tileDao.save(tile,user,mapLayer);
			}

			// tile's shift to canvas
			
			// construct image
			


		}return null;

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
