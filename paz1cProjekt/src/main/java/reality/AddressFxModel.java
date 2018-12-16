package reality;

import entities.Address;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class AddressFxModel {
	private Long id;
	private StringProperty state  = new SimpleStringProperty();
	private StringProperty city = new SimpleStringProperty();
	private StringProperty street  = new SimpleStringProperty();
	private StringProperty number  = new SimpleStringProperty();
	private StringProperty zipCode = new SimpleStringProperty();
	private StringProperty longitude = new SimpleStringProperty();
	private StringProperty latitude = new SimpleStringProperty();
	
	public Address getAddress() {
		Address address = new Address();
		address.setCity(getCity());
		address.setState(getState());
		address.setStreet(getStreet());
		address.setNumber(getNumber());
		address.setZipCode(getZipCode());
		address.setLongitude(getLongitude());
		address.setLatitude(getLatitude());
		
		return address;
	}
	
	public void setAddress(Address address) {
		this.id = address.getId();
		setCity(address.getCity());
		setState(address.getState());
		setStreet(address.getStreet());
		setNumber(address.getNumber());
		setZipCode(address.getZipCode());
		setLongitude(address.getLongitude());
		setLatitude(address.getLatitude());
	}

	public void setLatitude(String latitude) {
		this.latitude.set(latitude);
	}

	public String getLatitude() {
		return latitude.get();
	}

	public StringProperty latitudeProperty() {
		return latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude.set(longitude);
	}

	public String getLongitude() {
		return longitude.get();
	}

	public StringProperty longitudeProperty() {
		return longitude;
	}

	public void setNumber(String number) {
		this.number.setValue(number);
	}

	public String getNumber() {
		return number.getValue();
	}

	public StringProperty numberProperty() {
		return number;
	}

	public void setState(String state) {
		this.state.set(state);
	}

	public String getState() {
		return state.get();
	}

	public StringProperty stateProperty() {
		return state;
	}

	public void setCity(String city) {
		this.city.set(city);
	}

	public String getCity() {
		return city.get();
	}

	public StringProperty cityProperty() {
		return city;
	}

	public void setStreet(String street) {
		this.street.set(street);
	}

	public String getStreet() {
		return street.get();
	}

	public StringProperty streetProperty() {
		return street;
	}

	public void setZipCode(String zipCode) {
		this.zipCode.set(zipCode);
	}

	public String getZipCode() {
		return zipCode.get();
	}

	public StringProperty zipCodeProperty() {
		return zipCode;
	}

}
