package entities;

public class Tile {

	@Override
	public String toString() {
		return "Tile [longitude=" + longitude + ", latitude=" + latitude + ", zoom=" + zoom + ", cachedLocation="
				+ cachedLocation + "]";
	}

	private Long id;
	private int longitude; // x
	private int latitude; // y
	private int zoom;
	private String thumbnail;
	private String cachedLocation;
	private String fileFormat;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getLongitude() {
		return longitude;
	}

	public void setLongitude(int longitude) {
		this.longitude = longitude;
	}

	public int getLatitude() {
		return latitude;
	}

	public void setLatitude(int latitude) {
		this.latitude = latitude;
	}

	public int getZoom() {
		return zoom;
	}

	public void setZoom(int zoom) {
		this.zoom = zoom;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getCachedLocation() {
		return cachedLocation;
	}

	public void setCachedLocation(String cachedLocation) {
		this.cachedLocation = cachedLocation;
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	

}
