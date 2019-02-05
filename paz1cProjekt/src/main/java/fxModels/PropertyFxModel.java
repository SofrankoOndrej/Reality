package fxModels;

import entities.Property;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyFxModel {
	private Long id;
	private StringProperty name = new SimpleStringProperty();
//	private Address address = new Address();
	private StringProperty rating = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty();
	private StringProperty description = new SimpleStringProperty();
	private StringProperty url = new SimpleStringProperty();
	private StringProperty price = new SimpleStringProperty();

	public void setProperty(Property property) {
		if (property == null){
			return;
		}
		this.id = property.getId();
		setName(property.getName());
		setRating(property.getRating());
		setType(property.getType());
		setDescription(property.getDescription());
		setUrl(property.getUrl());
		setPrice(property.getPrice());
	}

	public Property getProperty() {
		Property property = new Property();
		property.setId(id);
		property.setName(getName());
		property.setRating(getRating());
		property.setType(getType());
		property.setDescription(getDescription());
		property.setUrl(getUrl());
		property.setPrice(getPrice());
		return property;
	}

	public String getPrice() {
		return price.get();
	}

	public void setPrice(String price) {
		this.price.set(price);
	}

	public StringProperty priceProperty() {
		return price;
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

	public String getDescription() {
		return description.get();
	}

	public void setDescription(String description) {
		this.description.set(description);
	}

	public StringProperty descriptionProperty() {
		return description;
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

	public String getRating() {
		return rating.get();
	}

	public void setRating(String rating) {
		this.rating.set(rating);
	}

	public StringProperty ratingProperty() {
		return rating;
	}

	public String getType() {
		return type.get();
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public StringProperty typeProperty() {
		return type;
	}
}
