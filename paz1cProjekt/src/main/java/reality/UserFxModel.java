package reality;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserFxModel {

//	StringProperty id = new SimpleStringProperty();
	StringProperty name = new SimpleStringProperty();
	StringProperty surname = new SimpleStringProperty();
	StringProperty email = new SimpleStringProperty();
	StringProperty username = new SimpleStringProperty();
	StringProperty password = new SimpleStringProperty();
	
//	public String getId() {
//		return id.get();
//	}
//	public void setId(String id) {
//		this.id.set(id);
//	}
//	public StringProperty idProperty() {
//		return id;
//	}
	
	public String getName() {
		return name.get();
	}
	public void setName(String name) {
		this.name.set(name);;
	}
	public StringProperty nameProperty() {
		return name;
	}
	
	public String getSurname() {
		return surname.get();
	}
	public void setSurname(String surname) {
		this.surname.set(surname);;
	}
	public StringProperty surnameProperty() {
		return surname;
	}
	
	public String getEmail() {
		return email.get();
	}
	public void setEmail(String email) {
		this.email.set(email);;
	}
	public StringProperty emailProperty() {
		return email;
	}
	
	public String getUsername() {
		return username.get();
	}
	public void setUsername(String username) {
		this.username.set(username);;
	}
	public StringProperty usernameProperty() {
		return username;
	}
	
	public String getPassword() {
		return password.get();
	}
	public void setPassword(String password) {
		this.password.set(password);;
	}
	public StringProperty passwordProperty() {
		return password;
	}
	
	public User getUser() {
		User user = new User();
		user.setName(getName());
		user.setSurname(getSurname());
		user.setEmail(getEmail());
		user.setUsername(getUsername());
		user.setPassword(getPassword());
		
		return user;
	}
	
	@Override
	public String toString() {
		return getUser().toString();
	}
}
