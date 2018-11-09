package reality;

import entities.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CreateUserController {

	@FXML
	private Button createuserButton;

	@FXML
	private PasswordField passwordTextfield;

	@FXML
	private TextField nameTextfield;

	@FXML
	private TextField surnameTextfield;

	@FXML
	private TextField emailTextfield;

	@FXML
	private TextField usernameTextfield;

	private UserFxModel createdUser = new UserFxModel();

	public CreateUserController() {

	}

	@FXML
	void initialize() {
		nameTextfield.textProperty().bindBidirectional(createdUser.nameProperty());
		surnameTextfield.textProperty().bindBidirectional(createdUser.surnameProperty());
		emailTextfield.textProperty().bindBidirectional(createdUser.emailProperty());
		usernameTextfield.textProperty().bindBidirectional(createdUser.usernameProperty());
		passwordTextfield.textProperty().bindBidirectional(createdUser.passwordProperty());

		createuserButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				System.out.println(createdUser.toString());
			}
		});

	}
}
