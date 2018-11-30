package reality;

import entities.MapLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MapLayerFxModel {

	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty mapServerUrl = new SimpleStringProperty();
	private StringProperty sampleTileUrl = new SimpleStringProperty();

	public MapLayer getMapLayer() {
		MapLayer mapLayer = new MapLayer();
		mapLayer.setId(getId());
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
		return mapServerUrl.get();
	}
	public void setUrl(String url) {
		this.mapServerUrl.set(url);
	}
	public StringProperty mapServerUrlProperty() {
		return mapServerUrl;
	}

	public String getSampleTileUrl() {
		return sampleTileUrl.get();
	}

	public void setSampleTileUrl(String sampleTileUrl) {
		this.sampleTileUrl.set(sampleTileUrl);
	}
	
	public StringProperty sampleTileUrlProperty() {
		return sampleTileUrl;
	}
}
