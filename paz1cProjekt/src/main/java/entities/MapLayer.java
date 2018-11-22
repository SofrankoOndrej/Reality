package entities;

public class MapLayer {

	private Long id;
	private String name;
	private String url;

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
		return "Map layer: [id: " + getId() + ", name: " + getName() + "URL: " + url.toString() +  " ]";
	}
}
