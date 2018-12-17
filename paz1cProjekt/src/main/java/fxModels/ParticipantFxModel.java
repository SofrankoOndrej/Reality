package fxModels;

import entities.Participant;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ParticipantFxModel {

	private StringProperty name= new SimpleStringProperty();
	private StringProperty surname = new SimpleStringProperty();
	private StringProperty role = new SimpleStringProperty();
	private StringProperty phone = new SimpleStringProperty();
	private StringProperty email = new SimpleStringProperty();
	
	public void setParticipant(Participant participant) {
		//	this.id = property.getId();
			setName(participant.getName());
			setSurname(participant.getSurname());
			setRole(participant.getRole());
			setPhone(participant.getPhone());
			setEmail(participant.getEmail());
		}
		
		public Participant getParticipant() {
			Participant participant = new Participant();
			participant.setName(getName());
			participant.setSurname(getSurname());
			participant.setRole(getRole());
			participant.setPhone(getPhone());
			participant.setEmail(getEmail());
			
			return participant;
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
		
		public String getPhone() {
			return phone.get();
		}
		public void setPhone(String phone) {
			this.phone.set(phone);
		}
		public StringProperty phoneProperty() {
			return phone;
		}
		
		public String getRole() {
			return role.get();
		}
		public void setRole(String role) {
			this.role.set(role);
		}
		public StringProperty roleProperty() {
			return role;
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
		
		public String getName() {
			return name.get();
		}

		public void setName(String name) {
			this.setName(name);
		}

		public StringProperty nameProperty() {
			return name;
		}
		
}
