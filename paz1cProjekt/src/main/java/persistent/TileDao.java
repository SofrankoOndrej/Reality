package persistent;

import java.util.List;

import entities.MapLayer;
import entities.Tile;

public interface TileDao {
	
	void add(Tile tile);
	
	Tile save(Tile tile);
	
	boolean isTileCached(Tile tile);
	
	String getTileCacheLocation(Tile tile);
	
	Tile getFullTile(Tile tile);
	
	List<Tile> getAll(MapLayer mapLayer);
	
	List<Tile> getAll(Tile tile);
}
