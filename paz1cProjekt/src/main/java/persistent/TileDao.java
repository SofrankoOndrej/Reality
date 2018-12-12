package persistent;

import java.util.List;

import entities.MapLayer;
import entities.Tile;
import entities.User;

public interface TileDao {
	
	void add(Tile tile);
	
	Tile save(Tile tile);
	
	String getTileCacheLocation(Tile tile);
	
	List<Tile> getAll(MapLayer mapLayer);
	
	List<Tile> getAll(Tile tile);

	Tile save(Tile tile, User user, MapLayer mapLayer);

	//Tile getFullTile(Tile tile);
	Tile getFullTile(User user, MapLayer mapLayer, Tile tile);

	//boolean isTileCached(Tile tile);
	boolean isTileCached(User user, MapLayer mapLayer, Tile tile);
}
