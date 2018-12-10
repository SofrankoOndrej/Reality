package reality;

import entities.Tile;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

public class TileFxModel {

	private Long id;
	private IntegerProperty longitude; // x
	private IntegerProperty latitude; // y
	private IntegerProperty zoom;
	private StringProperty thumbnail;
	private StringProperty cachedLocation;
	private StringProperty fileFormat;

	public String getFileFormat() {
		return fileFormat.get();
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat.set(fileFormat);
	}

	public StringProperty fileFormatProperty() {
		return fileFormat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCachedLocation(String cachedLocation) {
		this.cachedLocation.set(cachedLocation);
	}

	public String getCachedLocation() {
		return cachedLocation.get();
	}

	public StringProperty cachedLocationProperty() {
		return cachedLocation;
	}

	public void setLongitude(int longitude) {
		this.longitude.set(longitude);
	}

	public int getLongitude() {
		return this.longitude.get();
	}

	public IntegerProperty longitudeProperty() {
		return longitude;
	}

	public void setLatitude(int latitude) {
		this.latitude.set(latitude);
	}

	public int getLatitude() {
		return this.latitude.get();
	}

	public IntegerProperty latitudeProperty() {
		return latitude;
	}

	public void setZoom(int zoom) {
		this.zoom.set(zoom);
	}

	public int getZoom() {
		return this.zoom.get();
	}

	public IntegerProperty zoomProperty() {
		return zoom;
	}

	public String getThumbnail() {
		return thumbnail.get();
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail.set(thumbnail);
	}

	public StringProperty nameProperty() {
		return thumbnail;
	}

	public Tile getTile() {
		Tile tile = new Tile();
		tile.setId(getId());
		tile.setLatitude(getLatitude());
		tile.setLongitude(getLongitude());
		tile.setZoom(getZoom());
		tile.setThumbnail(getThumbnail());
		tile.setCachedLocation(getCachedLocation());
		tile.setFileFormat(getFileFormat());

		return tile;
	}

	public void setTile(Tile tile) {
		setId(tile.getId());
		setLongitude(tile.getLongitude());
		setLatitude(tile.getLatitude());
		setZoom(tile.getZoom());
		setThumbnail(tile.getThumbnail());
		setCachedLocation(tile.getCachedLocation());
		setFileFormat(tile.getFileFormat());
	}
}
