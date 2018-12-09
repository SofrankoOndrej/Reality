package util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
		// sprav URL pola typu mapy
		String constructedUrl = MapUtils.constructUrl(mapLayer, tile);
		Image tileImage = new Image(constructedUrl,true);
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
				String tileCachePath = tempPath + tile.getZoom() + "/" + tile.getLongitude() + "/" + tile.getLatitude();
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
