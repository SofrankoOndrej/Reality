package reality;

import entities.MapLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MapLayerFxModel {

	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty url = new SimpleStringProperty();

	public MapLayer getMapLayer() {
		MapLayer mapLayer = new MapLayer();
		mapLayer.setId(id);
		mapLayer.setName(getName());
		mapLayer.setUrl(getUrl());
		return mapLayer;
	}
	
	public void setMapLayer(MapLayer mapLayer) {
		setId(mapLayer.getId());
		setName(mapLayer.getName());
		setUrl(mapLayer.getUrl());
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
		
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getUrl() {
		return url.get();
	}
	public void setUrl(String url) {
		this.url.set(url);
	}
	public StringProperty urlProperty() {
		return url;
	}
}
