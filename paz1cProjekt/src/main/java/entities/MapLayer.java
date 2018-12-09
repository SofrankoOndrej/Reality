package entities;

public class MapLayer {

	private Long id;
	private String name;
	private String mapServerUrl;
	private String sampleTileUrl;
	private String cacheFolderPath;
	private String tileUrlFormat;

	public String getSampleTileUrl() {
		return sampleTileUrl;
	}

	public void setSampleTileUrl(String sampleTileUrl) {
		this.sampleTileUrl = sampleTileUrl;
	}

	public String getMapServerUrl() {
		return mapServerUrl;
	}

	public void setMapServerUrl(String mapServerUrl) {
		this.mapServerUrl = mapServerUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCacheFolderPath() {
		return cacheFolderPath;
	}

	public void setCacheFolderPath(String cacheFolderPath) {
		this.cacheFolderPath = cacheFolderPath;
	}

	public String getTileUrlFormat() {
		return tileUrlFormat;
	}

	public void setTileUrlFormat(String tileUrlFormat) {
		this.tileUrlFormat = tileUrlFormat;
	}

	@Override
	public String toString() {
		return "Map layer: [id: " + getId() + ", name: " + getName() + "URL: " + getMapServerUrl() + "Sample Tile URL: "
				+ getSampleTileUrl() + " ]";
	}
}
