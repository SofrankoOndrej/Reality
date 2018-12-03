package reality;

import entities.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class UserFxModel {

	private Long id;
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
