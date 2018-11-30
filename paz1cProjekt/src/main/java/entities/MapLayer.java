package entities;

public class MapLayer {

	private Long id;
	private String name;
	private String url;
	private String sampleTileUrl;

	public String getSampleTileUrl() {
		return sampleTileUrl;
	}

	public void setSampleTileUrl(String sampleTileUrl) {
		this.sampleTileUrl = sampleTileUrl;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@Override
	public String toString() {
		return "Map layer: [id: " + getId() + ", name: " + getName() + "URL: " + getUrl() + "Sample Tile URL: "
				+ getSampleTileUrl() + " ]";
	}
}
