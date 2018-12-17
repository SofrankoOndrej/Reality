package fxModels;

import entities.Property;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PropertyFxModel {
	private Long id;
	private StringProperty name = new SimpleStringProperty();
//	private Address address = new Address();
	private StringProperty rating = new SimpleStringProperty();
	private StringProperty type = new SimpleStringProperty();
	
	public void setProperty(Property property) {
		this.id = property.getId();
		setName(property.getName());
		setRating(property.getRating());
		setType(property.getType());
	}
	
	public Property getProperty() {
		Property property = new Property();
		property.setId(id);
		property.setName(getName());
		property.setRating(getRating());
		property.setType(getType());
		
		return property;
	}
	public Property getProperty1() {
		Property property = new Property();
		property.setId(id);
		property.setName(getName());
		property.setRating(getRating());
		property.setType(getType());
		
		return property;
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
