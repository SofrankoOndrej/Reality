package fxModels;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class UserFxModel {

	private Long id;
	private StringProperty lastBoundingBox = new SimpleStringProperty();
	private IntegerProperty lastZoom = new SimpleIntegerProperty();
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();
	private StringProperty cacheFolderPath = new SimpleStringProperty();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	// opatrenie aby zoom bol stale integer
	public void setLastZoom(double lastZoom) {
		this.lastZoom.set((int) lastZoom);
	}
	public int getLastZoom() {
		return this.lastZoom.get();
	}
	public IntegerProperty lastZoomProperty() {
		return lastZoom;
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

	public StringProperty lastBoundingBoxProperty() {
		return lastBoundingBox;
	}

	public String getLastBoundingBox() {
		return lastBoundingBox.get();
	}

	public void setLastBoundingBox(String lastBoundingBox) {
		this.lastBoundingBox.set(lastBoundingBox);
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

	public String getSurname() {
		return surname.get();
	}

	public void setSurname(String surname) {
		this.surname.set(surname);
	}

	public StringProperty surnameProperty() {
		return surname;
	}

	public String getEmail() {
		return email.get();
	}

	public void setEmail(String email) {
		this.email.set(email);
	}

	public StringProperty emailProperty() {
		return email;
	}

	public String getUsername() {
		return username.get();
	}

	public void setUsername(String username) {
		this.username.set(username);
	}

	public StringProperty usernameProperty() {
		return username;
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public User getUser() {
		User user = new User();
		user.setId(this.getId());
		user.setName(this.getName());
		user.setSurname(this.getSurname());
		user.setEmail(this.getEmail());
		user.setUsername(this.getUsername());
		user.setPassword(this.getPassword());
		user.setCacheFolderPath(this.getCacheFolderPath());
		user.setLastBoundingBox(this.getLastBoundingBox());
		user.setLastZoom(this.getLastZoom());

		return user;
	}

	public void setUser(User user) {
		setId(user.getId());
		setName(user.getName());
		setSurname(user.getSurname());
		setEmail(user.getEmail());
		setUsername(user.getUsername());
		setPassword(user.getPassword());
		setCacheFolderPath(user.getCacheFolderPath());
		setLastBoundingBox(user.getLastBoundingBox());
		setLastZoom(user.getLastZoom());
	}

	@Override
	public String toString() {
		return getUser().toString();
	}
}
