package fxModels;

import entities.MapLayer;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class MapLayerFxModel {

	private Long id;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty mapServerUrl = new SimpleStringProperty();
	private StringProperty sampleTileUrl = new SimpleStringProperty();
	private StringProperty cacheFolderPath = new SimpleStringProperty();
	private StringProperty tileUrlFormat = new SimpleStringProperty();

	public MapLayer getMapLayer() {
		MapLayer mapLayer = new MapLayer();
		mapLayer.setId(getId());
		mapLayer.setName(getName());
		mapLayer.setMapServerUrl(getUrl());
		mapLayer.setSampleTileUrl(getSampleTileUrl());
		mapLayer.setCacheFolderPath(getCacheFolderPath());
		mapLayer.setTileUrlFormat(getTileUrlFormat());
		return mapLayer;
	}

	public void setMapLayer(MapLayer mapLayer) {
		this.setId(mapLayer.getId());
		this.setName(mapLayer.getName());
		this.setUrl(mapLayer.getMapServerUrl());
		this.setSampleTileUrl(mapLayer.getSampleTileUrl());
		this.setCacheFolderPath(mapLayer.getCacheFolderPath());
		this.setTileUrlFormat(mapLayer.getTileUrlFormat());
	}

	public void setTileUrlFormat(String tileUrlFormat) {
		this.tileUrlFormat.set(tileUrlFormat);
	}

	public String getTileUrlFormat() {
		return tileUrlFormat.get();
	}

	public StringProperty tileUrlFormatProperty() {
		return tileUrlFormat;
	}

	public void setCacheFolderPath(String cacheFolderPath) {
		this.cacheFolderPath.set(cacheFolderPath);
	}

	public String getCacheFolderPath() {
		return this.cacheFolderPath.get();
	}

	public StringProperty cacheFolderPathProperty() {
		return cacheFolderPath;
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
