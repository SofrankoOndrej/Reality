package reality;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.DoubleProperty;

public class UserFxModel {

	private Long id;
	private DoubleProperty[][] lastBoundingBox;
	private StringProperty name = new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	private StringProperty username = new SimpleStringProperty();
	private StringProperty password = new SimpleStringProperty();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public DoubleProperty[][] lastBoundingBoxProperty() {
		return lastBoundingBox;
	}

	public Double[][] getLastBoundingBox() {
		Double[][] multiPoint = new Double[2][2];
		// napln multipoint
		multiPoint[0][0] = lastBoundingBox[0][0].get();
		multiPoint[0][1] = lastBoundingBox[0][1].get();
		multiPoint[1][0] = lastBoundingBox[1][0].get();
		multiPoint[1][1] = lastBoundingBox[1][1].get();
		return multiPoint;
	}

	public void setLastBoundingBox(Double[][] lastBoundingBox) {
		// napln DoubleProperty
		this.lastBoundingBox[0][0].set(lastBoundingBox[0][0]);
		this.lastBoundingBox[0][1].set(lastBoundingBox[0][1]);
		this.lastBoundingBox[1][0].set(lastBoundingBox[1][0]);
		this.lastBoundingBox[1][1].set(lastBoundingBox[1][1]);
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

		return user;
	}

	public void setUser(User user) {
		setId(user.getId());
		setName(user.getName());
		setSurname(user.getSurname());
		setEmail(user.getEmail());
		setUsername(user.getUsername());
		setPassword(user.getPassword());
	}

	@Override
	public String toString() {
		return getUser().toString();
	}
}
